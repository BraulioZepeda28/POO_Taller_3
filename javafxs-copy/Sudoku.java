public class Sudoku {
    private int[][] tablero;
    private boolean[][] casillasFijas;

    
    
    
    
    
    
    
    public Sudoku() {
        tablero = new int[9][9];
        casillasFijas = new boolean[9][9];
        cargarTablero(1); 
    }

    
    
    
    
    
    
    
public void cargarTablero(int opcion) {
        int[][] plantilla;
        
        if (opcion == 1) {
            
            plantilla = new int[][] {
                {5,3,4, 0,7,0, 9,1,2},
                {6,0,0, 1,9,5, 0,0,8},
                {0,9,8, 0,0,0, 0,6,0},
                {8,0,0, 0,6,0, 0,0,3},
                {4,0,0, 8,0,3, 0,0,1},
                {7,0,0, 0,2,0, 0,0,6},
                {0,6,0, 0,0,0, 2,8,4},
                {2,8,0, 4,1,9, 0,0,5},
                {3,4,5, 0,8,0, 0,7,9}
            };
        } else if (opcion == 2) {
            
            plantilla = new int[][] {
                {0,2,0, 6,0,8, 0,0,0},
                {5,8,0, 0,0,9, 7,0,0},
                {0,0,0, 0,4,0, 0,0,0},
                {3,7,0, 0,0,0, 5,0,0},
                {6,0,0, 0,0,0, 0,0,4},
                {0,0,8, 0,0,0, 0,1,3},
                {0,0,0, 0,2,0, 0,0,0},
                {0,0,9, 8,0,0, 0,3,6},
                {0,0,0, 3,0,6, 0,9,0}
            };
        } else {
          
            plantilla = new int[][] {
                {8,0,0, 0,0,0, 0,0,0},
                {0,0,3, 6,0,0, 0,0,0},
                {0,7,0, 0,9,0, 2,0,0},
                {0,5,0, 0,0,7, 0,0,0},
                {0,0,0, 0,4,5, 7,0,0},
                {0,0,0, 1,0,0, 0,3,0},
                {0,0,1, 0,0,0, 0,6,8},
                {0,0,8, 5,0,0, 0,1,0},
                {0,9,0, 0,0,0, 4,0,0}
            };
        }

        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                int numeroActual = plantilla[f][c];
                tablero[f][c] = numeroActual;
                
                if (numeroActual != 0) {
                    casillasFijas[f][c] = true;
                } else {
                    casillasFijas[f][c] = false;
                }
            }
        }
    }

    
    
    
    
    
    
    
    
    public boolean validarFila(int fila, int colActual, int num) {
        for (int c = 0; c < 9; c++) {
    
            if (c == colActual) {
                continue;
            }
            
            if (tablero[fila][c] == num) {
                return false;
            }
        }
        return true;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    public boolean validarColumna(int filaActual, int col, int num) {
        for (int f = 0; f < 9; f++) {
       
            if (f == filaActual) {
                continue;
            }
            
            if (tablero[f][col] == num) {
                return false;
            }
        }
        return true;
    }

    
    
    
    
    
    
    
    
    
    
    
    public boolean validarCuadricula(int fila, int col, int num) {
        int inicioFila = 0;
        int inicioCol = 0;

        if (fila >= 0 && fila <= 2) {
            inicioFila = 0;
        } else if (fila >= 3 && fila <= 5) {
            inicioFila = 3;
        } else if (fila >= 6 && fila <= 8) {
            inicioFila = 6;
        }

        if (col >= 0 && col <= 2) {
            inicioCol = 0;
        } else if (col >= 3 && col <= 5) {
            inicioCol = 3;
        } else if (col >= 6 && col <= 8) {
            inicioCol = 6;
        }

        for (int f = inicioFila; f < inicioFila + 3; f++) {
            for (int c = inicioCol; c < inicioCol + 3; c++) {
                
                if (f == fila && c == col) {
                    continue;
                }
                
                if (tablero[f][c] == num) {
                    return false;
                }
            }
        }
        return true;
    }


    
    
    
    
    
    public boolean esMovimientoValido(int fila, int col, int num) {

        if (num < 1) {
            return false;
        }
        if (num > 9) {
            return false;
        }

        if (casillasFijas[fila][col] == true) {
            return false;
        }

        boolean filaCorrecta = validarFila(fila, col, num);
        boolean columnaCorrecta = validarColumna(fila, col, num);
        boolean cuadriculaCorrecta = validarCuadricula(fila, col, num);

        if (filaCorrecta == true && columnaCorrecta == true && cuadriculaCorrecta == true) {
            return true;
        } else {
            return false;
        }
    }

    
    
    
    
    
    
    
    public int getValor(int f, int c) { 
        return tablero[f][c]; 
    }
    public void setValor(int f, int c, int val) { 
        tablero[f][c] = val; 
    }
    public boolean esFija(int f, int c) { 
        return casillasFijas[f][c]; 
    }
 
    
    
    
    
    
    
    
    
    public boolean juegoTerminado() {
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                int num = tablero[f][c];

                if (num == 0) {
                    return false;
                }

                boolean filaOk = validarFila(f, c, num);
                boolean colOk = validarColumna(f, c, num);
                boolean cuadOk = validarCuadricula(f, c, num);

                if (filaOk == false || colOk == false || cuadOk == false) {
                    return false; 
                }
            }
        }
        return true; 
    }
    
    
    
    
    
    
}