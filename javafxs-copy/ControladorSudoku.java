import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControladorSudoku {

    private Sudoku modelo;
    private VistaSudoku vista;
    private int ultimaFila = -1;
    private int ultimaCol = -1;

    public ControladorSudoku(Sudoku modeloRecibido, VistaSudoku vistaRecibida) {
        this.modelo = modeloRecibido;
        this.vista = vistaRecibida;
    }

    public void iniciarJuego() {
        configurarEventosCasillas();
        configurarEventosBotones();
        cambiarPlantilla(1);
    }

    private void configurarEventosCasillas() {
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                TextField cajita = vista.getCasilla(f, c);
                final int filaActual = f;
                final int colActual = c;

                cajita.setOnKeyReleased(evento -> {
                    revisarEntradaUsuario(filaActual, colActual);
                });
            }
        }
    }

    private void configurarEventosBotones() {
        Button b1 = vista.getBtnFacil();
        Button b2 = vista.getBtnNormal();
        Button b3 = vista.getBtnDificil();

        b1.setOnAction(evento -> {
            cambiarPlantilla(1);
        });

        b2.setOnAction(evento -> {
            cambiarPlantilla(2);
        });

        b3.setOnAction(evento -> {
            cambiarPlantilla(3);
        });
    }

    public void cambiarPlantilla(int numPlantilla) {
        modelo.cargarTablero(numPlantilla);
        vista.marcarBotonSeleccionado(numPlantilla);

        ultimaFila = -1;
        ultimaCol = -1;

        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                int valorNumerico = modelo.getValor(f, c);
                TextField cajitaActual = vista.getCasilla(f, c);
                boolean esCasillaFija = modelo.esFija(f, c);

                if (esCasillaFija == true) {
                    cajitaActual.setText("" + valorNumerico);
                    cajitaActual.setEditable(false);
                    vista.aplicarEstiloCasilla(f, c, "#1e1e1e", "#b0b0b0");
                } else {
                    cajitaActual.setText("");
                    cajitaActual.setEditable(true);
                    vista.aplicarEstiloCasilla(f, c, "#2d2d2d", "white");
                }
            }
        }
    }

    public void revisarEntradaUsuario(int fila, int col) {
        if (modelo.esFija(fila, col) == true) {
            return;
        }

        TextField cajita = vista.getCasilla(fila, col);
        String textoEscrito = cajita.getText().trim();

        if (textoEscrito.length() != 1) {
            modelo.setValor(fila, col, 0);
            cajita.setText("");
            vista.aplicarEstiloCasilla(fila, col, "#2d2d2d", "white");
            return;
        }

        char letra = textoEscrito.charAt(0);

        if (letra >= '1' && letra <= '9') {
            int numero = Integer.parseInt(textoEscrito);

            if (fila == ultimaFila && col == ultimaCol && modelo.getValor(fila, col) == numero) {
                return;
            }

            if (ultimaFila != -1 && ultimaCol != -1) {
                if (ultimaFila != fila || ultimaCol != col) {
                    TextField cajitaAnterior = vista.getCasilla(ultimaFila, ultimaCol);
                    String textoAnterior = cajitaAnterior.getText().trim();

                    if (!textoAnterior.equals("")) {
                        vista.aplicarEstiloCasilla(ultimaFila, ultimaCol, "#2d2d2d", "#b0b0b0");
                    }
                }
            }

            ultimaFila = fila;
            ultimaCol = col;

            boolean esValido = modelo.esMovimientoValido(fila, col, numero);
            modelo.setValor(fila, col, numero);

            if (esValido == true) {
                vista.aplicarEstiloCasilla(fila, col, "#2d2d2d", "#4caf50");

                if (modelo.juegoTerminado() == true) {
                    vista.mostrarMensajeVictoria();
                }
            } else {
                vista.aplicarEstiloCasilla(fila, col, "#2d2d2d", "#ff5252");
            }
        } else {
            modelo.setValor(fila, col, 0);
            cajita.setText("");
            vista.aplicarEstiloCasilla(fila, col, "#2d2d2d", "white");
        }
    }
}