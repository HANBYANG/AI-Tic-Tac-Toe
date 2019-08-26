import java.util.*;
public class aiTicTacToe3 {
    public int one = 1;
    public int two = 3;
    public int three = 7;
    public int four = 800;
    public int arg = 2;

    public int player; //1 for player 1 and 2 for player 2
    private List<List<positionTicTacToe>>  winningLines = initializeWinningLines();
    private int currentDepth = 0;
    public int maxDepth = 1;

    private int getStateOfPositionFromBoard(positionTicTacToe position, List<positionTicTacToe> board)
    {
        //a helper function to get state of a certain position in the Tic-Tac-Toe board by given position TicTacToe
        int index = position.x*16+position.y*4+position.z;
        return board.get(index).state;
    }

    private void setStateOfPositionOnBoard(positionTicTacToe position, List<positionTicTacToe> board, int state) {
        int index = position.x*16+position.y*4+position.z;
        board.get(index).state = state;
    }

    public positionTicTacToe myAIAlgorithm2(List<positionTicTacToe> board, int player)
    {
        //TODO: this is where you are going to implement your AI algorithm to win the game. The default is an AI randomly choose any available move.
        positionTicTacToe bestMove = new positionTicTacToe(0,0,0);

        int bestMin = 2000;
        int bestMax = -2000;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {

                    positionTicTacToe myNextMove = new positionTicTacToe(i, j, k);
                    if (getStateOfPositionFromBoard(myNextMove,board) ==0) {
                        setStateOfPositionOnBoard(myNextMove, board, player);
                        if (checkWin(board, player) == player) {
                            setStateOfPositionOnBoard(myNextMove, board, 0);
                            return myNextMove;
                        }
                        if (player == 1) {
                            int  hval = heuristicVal(board);
                            if (hval > bestMax) {
                                bestMax = hval;
                                bestMove = myNextMove;
                            }
                        } else {
                            int  hval = heuristicVal(board);
                            if (hval < bestMin) {
                                bestMin = hval;
                                bestMove = myNextMove;
                            }
                        }
                        setStateOfPositionOnBoard(myNextMove, board, 0);

                    }
                }
            }
        }
        return bestMove;

    }

    public positionTicTacToe myAIAlgorithm(List<positionTicTacToe> board, int player) {

        positionTicTacToe bestMove = null;
        int maxVal = -2000;
        int minVal = 2000;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {

                    positionTicTacToe myNextMove = new positionTicTacToe(i, j, k);

                    // skip positions that already have game pieces on them
                    if (getStateOfPositionFromBoard(myNextMove, board) == 0) {

                        setStateOfPositionOnBoard(myNextMove, board, player);

                        // check if myNextMove is a win
                        if (checkWin(board, player) == player) {
                            setStateOfPositionOnBoard(myNextMove, board, 0);
                            return myNextMove;
                        }
                        // else do recursion to find best move
                        currentDepth = 0;
                        if (player == 1) {
                            int calcVal = miniMax(board, 2, -1000, 1000);
                            if (calcVal > maxVal) {
                                maxVal = calcVal;
                                bestMove = myNextMove;
                            }
                            setStateOfPositionOnBoard(myNextMove, board, 0);
                        } else { // minimizing player
                            int calcVal = miniMax(board, 1, -1000, 1000);
                            if (calcVal < minVal) {
                                minVal = calcVal;
                                bestMove = myNextMove;
                            }
                            setStateOfPositionOnBoard(myNextMove, board, 0);
                        }
                    }
                }
            }
        }
//		System.out.println("Best move for player " + player + ": ");
//		bestMove.printPosition();
        return bestMove;
    }

    private int miniMax(List<positionTicTacToe> board, int player, int a, int b) {
        int alpha = a;
        int beta = b;

        if (currentDepth < maxDepth) {
            currentDepth++;
            if (player == 1) {

                int bestVal;

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < 4; k++) {
                            positionTicTacToe myNextMove = new positionTicTacToe(i, j, k);
                            // valid place to put piece on board
                            if (getStateOfPositionFromBoard(myNextMove, board) == 0) {

                                setStateOfPositionOnBoard(myNextMove, board, player);
                                if (checkWin(board, player) == 1) {
                                    setStateOfPositionOnBoard(myNextMove, board, 0);
                                    return 1000;
                                } else {
                                    // find best move recursively
                                    bestVal = miniMax(board, 2, a, b);
                                    if (bestVal > alpha) {
                                        alpha = bestVal;
                                    }
                                    setStateOfPositionOnBoard(myNextMove, board, 0);
                                }
                                if (alpha >= beta)
                                    break;
                            }
                        }
                    }
                }
                return alpha;
            }
            // player 2; minimizing
            else {
                int bestVal;

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < 4; k++) {
                            positionTicTacToe myNextMove = new positionTicTacToe(i, j, k);

                            // valid place to put piece on board
                            if (getStateOfPositionFromBoard(myNextMove, board) == 0) {

                                setStateOfPositionOnBoard(myNextMove, board, player);
                                if (checkWin(board, player) == 2) {
                                    setStateOfPositionOnBoard(myNextMove, board, 0);
                                    return -1000;
                                } else {
                                    // find best move recursively
                                    bestVal = miniMax(board, 1, a, b);
                                    if (bestVal < beta) {
                                        beta = bestVal;
                                    }
                                    setStateOfPositionOnBoard(myNextMove, board, 0);
                                }
                                if (alpha >= beta)
                                    break;
                            }
                        }
                    }
                }
                return beta;
            }
        }
        else {
            return heuristicVal(board);
        }
    }

    public int getScoreFor(int player,List<positionTicTacToe> board){
        int score = 0;
        List<positionTicTacToe> ptnlWinMoves = new ArrayList<>();
        for (List<positionTicTacToe> line:winningLines) {
            int lineNum = 0;
            positionTicTacToe ptnlWinMove = new positionTicTacToe(0,0,0);
            for(positionTicTacToe position:line){
                int state = getStateOfPositionFromBoard(position, board);
                if(state == player)
                    lineNum += 1;
                else if(state == 0){
                    ptnlWinMove = position;
                    continue;
                }
                else{
                    lineNum = -1;
                    break;
                }
            }
            if(lineNum == -1)
                continue;
            int temp = 0; //1<<(lineNum) - 1;//1->1; 2->3; 3->7; 4->15
            switch(lineNum){
                case 1:
                    temp = one;
                    break;
                case 2:
                    temp = two;
                    break;
                case 3:
                    temp = three;
                    break;
                case 4:
                    temp = four;
            }
            score += temp;
            if(temp == four){//find the wining step
                boolean flag = true;
                for(positionTicTacToe wMove:ptnlWinMoves){
                    if(wMove.x == ptnlWinMove.x && wMove.y == ptnlWinMove.y && wMove.z == ptnlWinMove.z) {
                        flag = false;
                        break;
                    }
                }
                if(flag) {
                    ptnlWinMoves.add(ptnlWinMove);
                }
            }
        }
        score += ptnlWinMoves.size()*arg;
        return score;
    }

    private int heuristicVal(List<positionTicTacToe> board) {
        int v1 = getScoreFor(1, board);
        int v2 = getScoreFor(2, board);
        return v1-v2;
    }

    // return number of possible wins for a given player for the current board
    private int numPosWins(List<positionTicTacToe> board, int player) {

        int numwins = 0;

        for (int i = 0; i < winningLines.size(); i++) {
            positionTicTacToe p0 = winningLines.get(i).get(0);
            positionTicTacToe p1 = winningLines.get(i).get(1);
            positionTicTacToe p2 = winningLines.get(i).get(2);
            positionTicTacToe p3 = winningLines.get(i).get(3);

            int state0 = getStateOfPositionFromBoard(p0, board);
            int state1 = getStateOfPositionFromBoard(p1, board);
            int state2 = getStateOfPositionFromBoard(p2, board);
            int state3 = getStateOfPositionFromBoard(p3, board);
            // if this is a winnable line for a particular player
            if ((state0 == 0 || state0 == player) && (state1 == 0 || state1 == player)
                    && (state2 == 0 || state2 == player) && (state3 == 0 || state3 == player)) {
                numwins++;
            }
        }
        return numwins;
    }

    //copied from runTicTacToe
    private int checkWin(List<positionTicTacToe> board, int player) {

        for(int i=0;i<winningLines.size();i++)
        {
            //goes through each winning line scenario and grabs the 4 positions for that winning line
            positionTicTacToe p0 = winningLines.get(i).get(0);
            positionTicTacToe p1 = winningLines.get(i).get(1);
            positionTicTacToe p2 = winningLines.get(i).get(2);
            positionTicTacToe p3 = winningLines.get(i).get(3);

            //get the current 4 states for the board of the winning line positions
            int state0 = getStateOfPositionFromBoard(p0,board);
            int state1 = getStateOfPositionFromBoard(p1,board);
            int state2 = getStateOfPositionFromBoard(p2,board);
            int state3 = getStateOfPositionFromBoard(p3,board);

            //if they have the same state (marked by same player) and they are not all marked.
            //logically this win will be caused by the current player's move because we would not be running this part
            //of the algo if the other player already won
            if(state0 == state1 && state1 == state2 && state2 == state3 && state0==player)
            {
                //someone wins
                p0.state = state0;
                p1.state = state1;
                p2.state = state2;
                p3.state = state3;

                return player;
            }
        }
        return 0;
    }


    private List<List<positionTicTacToe>> initializeWinningLines()
    {
        //create a list of winning line so that the game will "brute-force" check if a player satisfied any winning condition(s).
        List<List<positionTicTacToe>> winningLines = new ArrayList<List<positionTicTacToe>>();

        //48 straight winning lines
        //z axis winning lines
        for(int i = 0; i<4; i++)
            for(int j = 0; j<4;j++)
            {
                List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
                oneWinCondtion.add(new positionTicTacToe(i,j,0,-1));
                oneWinCondtion.add(new positionTicTacToe(i,j,1,-1));
                oneWinCondtion.add(new positionTicTacToe(i,j,2,-1));
                oneWinCondtion.add(new positionTicTacToe(i,j,3,-1));
                winningLines.add(oneWinCondtion);
            }
        //y axis winning lines
        for(int i = 0; i<4; i++)
            for(int j = 0; j<4;j++)
            {
                List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
                oneWinCondtion.add(new positionTicTacToe(i,0,j,-1));
                oneWinCondtion.add(new positionTicTacToe(i,1,j,-1));
                oneWinCondtion.add(new positionTicTacToe(i,2,j,-1));
                oneWinCondtion.add(new positionTicTacToe(i,3,j,-1));
                winningLines.add(oneWinCondtion);
            }
        //x axis winning lines
        for(int i = 0; i<4; i++)
            for(int j = 0; j<4;j++)
            {
                List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
                oneWinCondtion.add(new positionTicTacToe(0,i,j,-1));
                oneWinCondtion.add(new positionTicTacToe(1,i,j,-1));
                oneWinCondtion.add(new positionTicTacToe(2,i,j,-1));
                oneWinCondtion.add(new positionTicTacToe(3,i,j,-1));
                winningLines.add(oneWinCondtion);
            }

        //12 main diagonal winning lines
        //xz plane-4
        for(int i = 0; i<4; i++)
        {
            List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
            oneWinCondtion.add(new positionTicTacToe(0,i,0,-1));
            oneWinCondtion.add(new positionTicTacToe(1,i,1,-1));
            oneWinCondtion.add(new positionTicTacToe(2,i,2,-1));
            oneWinCondtion.add(new positionTicTacToe(3,i,3,-1));
            winningLines.add(oneWinCondtion);
        }
        //yz plane-4
        for(int i = 0; i<4; i++)
        {
            List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
            oneWinCondtion.add(new positionTicTacToe(i,0,0,-1));
            oneWinCondtion.add(new positionTicTacToe(i,1,1,-1));
            oneWinCondtion.add(new positionTicTacToe(i,2,2,-1));
            oneWinCondtion.add(new positionTicTacToe(i,3,3,-1));
            winningLines.add(oneWinCondtion);
        }
        //xy plane-4
        for(int i = 0; i<4; i++)
        {
            List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
            oneWinCondtion.add(new positionTicTacToe(0,0,i,-1));
            oneWinCondtion.add(new positionTicTacToe(1,1,i,-1));
            oneWinCondtion.add(new positionTicTacToe(2,2,i,-1));
            oneWinCondtion.add(new positionTicTacToe(3,3,i,-1));
            winningLines.add(oneWinCondtion);
        }

        //12 anti diagonal winning lines
        //xz plane-4
        for(int i = 0; i<4; i++)
        {
            List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
            oneWinCondtion.add(new positionTicTacToe(0,i,3,-1));
            oneWinCondtion.add(new positionTicTacToe(1,i,2,-1));
            oneWinCondtion.add(new positionTicTacToe(2,i,1,-1));
            oneWinCondtion.add(new positionTicTacToe(3,i,0,-1));
            winningLines.add(oneWinCondtion);
        }
        //yz plane-4
        for(int i = 0; i<4; i++)
        {
            List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
            oneWinCondtion.add(new positionTicTacToe(i,0,3,-1));
            oneWinCondtion.add(new positionTicTacToe(i,1,2,-1));
            oneWinCondtion.add(new positionTicTacToe(i,2,1,-1));
            oneWinCondtion.add(new positionTicTacToe(i,3,0,-1));
            winningLines.add(oneWinCondtion);
        }
        //xy plane-4
        for(int i = 0; i<4; i++)
        {
            List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
            oneWinCondtion.add(new positionTicTacToe(0,3,i,-1));
            oneWinCondtion.add(new positionTicTacToe(1,2,i,-1));
            oneWinCondtion.add(new positionTicTacToe(2,1,i,-1));
            oneWinCondtion.add(new positionTicTacToe(3,0,i,-1));
            winningLines.add(oneWinCondtion);
        }

        //4 additional diagonal winning lines
        List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
        oneWinCondtion.add(new positionTicTacToe(0,0,0,-1));
        oneWinCondtion.add(new positionTicTacToe(1,1,1,-1));
        oneWinCondtion.add(new positionTicTacToe(2,2,2,-1));
        oneWinCondtion.add(new positionTicTacToe(3,3,3,-1));
        winningLines.add(oneWinCondtion);

        oneWinCondtion = new ArrayList<positionTicTacToe>();
        oneWinCondtion.add(new positionTicTacToe(0,0,3,-1));
        oneWinCondtion.add(new positionTicTacToe(1,1,2,-1));
        oneWinCondtion.add(new positionTicTacToe(2,2,1,-1));
        oneWinCondtion.add(new positionTicTacToe(3,3,0,-1));
        winningLines.add(oneWinCondtion);

        oneWinCondtion = new ArrayList<positionTicTacToe>();
        oneWinCondtion.add(new positionTicTacToe(3,0,0,-1));
        oneWinCondtion.add(new positionTicTacToe(2,1,1,-1));
        oneWinCondtion.add(new positionTicTacToe(1,2,2,-1));
        oneWinCondtion.add(new positionTicTacToe(0,3,3,-1));
        winningLines.add(oneWinCondtion);

        oneWinCondtion = new ArrayList<positionTicTacToe>();
        oneWinCondtion.add(new positionTicTacToe(0,3,0,-1));
        oneWinCondtion.add(new positionTicTacToe(1,2,1,-1));
        oneWinCondtion.add(new positionTicTacToe(2,1,2,-1));
        oneWinCondtion.add(new positionTicTacToe(3,0,3,-1));
        winningLines.add(oneWinCondtion);

        return winningLines;

    }
    public aiTicTacToe3(int setPlayer)
    {
        player = setPlayer;
    }
}
