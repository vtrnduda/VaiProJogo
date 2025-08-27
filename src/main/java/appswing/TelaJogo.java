package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import model.Jogo;
import requisito.Fachada;

public class TelaJogo {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField textFieldDataHora;
    private JTextField textFieldLocal;
    private JTextField textFieldTimeA;
    private JTextField textFieldTimeB;
    private JTextField textFieldFotoTimeA;
    private JTextField textFieldFotoTimeB;
    private JButton buttonListar;
    private JButton buttonDeletar;
    private JButton buttonCadastrar;
    private JButton buttonLimpar;
    private JLabel labelMensagem;
    private JLabel labelDataHora;
    private JLabel labelLocal;
    private JLabel labelTimeA;
    private JLabel labelTimeB;
    private JLabel labelFotoTimeA;
    private JLabel labelFotoTimeB;
    private JLabel labelResultados;

    public TelaJogo() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("Jogo");
        frame.setBounds(100, 100, 850, 550);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                Fachada.inicializar();
                listagem();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Fachada.finalizar();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 43, 790, 148);
        frame.getContentPane().add(scrollPane);

        table = new JTable() {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        };
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (table.getSelectedRow() >= 0) {
                    Long id = (Long) table.getValueAt(table.getSelectedRow(), 0);
                    labelResultados.setText("selecionado=" + id);
                    buttonDeletar.setEnabled(true);
                }
            }
        });
        table.setGridColor(Color.BLACK);
        table.setRequestFocusEnabled(false);
        table.setFocusable(false);
        table.setBackground(new Color(144, 238, 144));
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        labelMensagem = new JLabel("");
        labelMensagem.setForeground(Color.BLUE);
        labelMensagem.setBounds(12, 500, 800, 14);
        frame.getContentPane().add(labelMensagem);

        labelResultados = new JLabel("resultados:");
        labelResultados.setBounds(21, 202, 431, 14);
        frame.getContentPane().add(labelResultados);

        buttonListar = new JButton("Listar");
        buttonListar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listagem();
            }
        });
        buttonListar.setBounds(375, 11, 89, 23);
        frame.getContentPane().add(buttonListar);

        labelDataHora = new JLabel("Data/Hora:");
        labelDataHora.setHorizontalAlignment(SwingConstants.LEFT);
        labelDataHora.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelDataHora.setBounds(21, 240, 80, 14);
        frame.getContentPane().add(labelDataHora);

        textFieldDataHora = new JTextField();
        textFieldDataHora.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldDataHora.setColumns(10);
        textFieldDataHora.setBounds(110, 237, 150, 20);
        textFieldDataHora.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                verificarCampos();
            }
        });
        frame.getContentPane().add(textFieldDataHora);

        labelLocal = new JLabel("Local:");
        labelLocal.setHorizontalAlignment(SwingConstants.LEFT);
        labelLocal.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelLocal.setBounds(280, 240, 40, 14);
        frame.getContentPane().add(labelLocal);

        textFieldLocal = new JTextField();
        textFieldLocal.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldLocal.setColumns(10);
        textFieldLocal.setBounds(330, 237, 200, 20);
        textFieldLocal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                verificarCampos();
            }
        });
        frame.getContentPane().add(textFieldLocal);

        labelTimeA = new JLabel("Time A:");
        labelTimeA.setHorizontalAlignment(SwingConstants.LEFT);
        labelTimeA.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelTimeA.setBounds(21, 280, 60, 14);
        frame.getContentPane().add(labelTimeA);

        textFieldTimeA = new JTextField();
        textFieldTimeA.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldTimeA.setColumns(10);
        textFieldTimeA.setBounds(90, 277, 150, 20);
        textFieldTimeA.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                verificarCampos();
            }
        });
        frame.getContentPane().add(textFieldTimeA);

        labelTimeB = new JLabel("Time B:");
        labelTimeB.setHorizontalAlignment(SwingConstants.LEFT);
        labelTimeB.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelTimeB.setBounds(260, 280, 60, 14);
        frame.getContentPane().add(labelTimeB);

        textFieldTimeB = new JTextField();
        textFieldTimeB.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldTimeB.setColumns(10);
        textFieldTimeB.setBounds(330, 277, 150, 20);
        textFieldTimeB.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                verificarCampos();
            }
        });
        frame.getContentPane().add(textFieldTimeB);

        labelFotoTimeA = new JLabel("Foto Time A:");
        labelFotoTimeA.setHorizontalAlignment(SwingConstants.LEFT);
        labelFotoTimeA.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelFotoTimeA.setBounds(21, 320, 80, 14);
        frame.getContentPane().add(labelFotoTimeA);

        textFieldFotoTimeA = new JTextField();
        textFieldFotoTimeA.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldFotoTimeA.setColumns(10);
        textFieldFotoTimeA.setBounds(110, 317, 200, 20);
        textFieldFotoTimeA.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                verificarCampos();
            }
        });
        frame.getContentPane().add(textFieldFotoTimeA);

        labelFotoTimeB = new JLabel("Foto Time B:");
        labelFotoTimeB.setHorizontalAlignment(SwingConstants.LEFT);
        labelFotoTimeB.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelFotoTimeB.setBounds(330, 320, 80, 14);
        frame.getContentPane().add(labelFotoTimeB);

        textFieldFotoTimeB = new JTextField();
        textFieldFotoTimeB.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldFotoTimeB.setColumns(10);
        textFieldFotoTimeB.setBounds(420, 317, 200, 20);
        textFieldFotoTimeB.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                verificarCampos();
            }
        });
        frame.getContentPane().add(textFieldFotoTimeB);

        buttonCadastrar = new JButton("Cadastrar");
        buttonCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String dataHoraTexto = textFieldDataHora.getText();
                    String local = textFieldLocal.getText();
                    String timeA = textFieldTimeA.getText();
                    String timeB = textFieldTimeB.getText();
                    String fotoTimeA = textFieldFotoTimeA.getText();
                    String fotoTimeB = textFieldFotoTimeB.getText();

                    String[] partesDataHora = dataHoraTexto.split(" ");
                    if (partesDataHora.length != 2) {
                        throw new RuntimeException("Formato inválido. Use: dd/MM/yyyy HH:mm");
                    }

                    DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

                    LocalDate data = LocalDate.parse(partesDataHora[0], formatoData);
                    LocalTime hora = LocalTime.parse(partesDataHora[1], formatoHora);

                    Fachada.cadastrarJogo(data, hora, local, timeA, timeB);
                    labelMensagem.setText("Jogo cadastrado com sucesso");
                    listagem();
                    limparTudo();
                } catch (DateTimeParseException ex) {
                    labelMensagem.setText("Formato de data/hora inválido. Use: dd/MM/yyyy HH:mm");
                } catch (Exception ex) {
                    labelMensagem.setText(ex.getMessage());
                }
            }
        });
        buttonCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonCadastrar.setBounds(21, 360, 120, 23);
        buttonCadastrar.setEnabled(false);
        frame.getContentPane().add(buttonCadastrar);

        buttonDeletar = new JButton("Deletar Selecionado");
        buttonDeletar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (table.getSelectedRow() >= 0) {
                        Long idJogo = (Long) table.getValueAt(table.getSelectedRow(), 0);

                        Fachada.removerJogo(idJogo);
                        labelMensagem.setText("Jogo removido com sucesso");
                        listagem();
                        limparTudo();
                    } else {
                        labelMensagem.setText("Nenhum jogo selecionado");
                    }
                } catch (Exception ex) {
                    labelMensagem.setText(ex.getMessage());
                }
            }
        });
        buttonDeletar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonDeletar.setBounds(160, 360, 170, 23);
        buttonDeletar.setEnabled(false);
        frame.getContentPane().add(buttonDeletar);

        buttonLimpar = new JButton("Limpar Tudo");
        buttonLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparTudo();
                labelMensagem.setText("Campos e seleção limpos");
            }
        });
        buttonLimpar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonLimpar.setBounds(350, 360, 120, 23);
        frame.getContentPane().add(buttonLimpar);
    }

    public void listagem() {
        try {
            List<Jogo> lista = Fachada.listarJogos();

            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            model.addColumn("id");
            model.addColumn("dataHora");
            model.addColumn("local");
            model.addColumn("timeA");
            model.addColumn("timeB");
            model.addColumn("fotoTimeA");
            model.addColumn("fotoTimeB");

            for (Jogo jogo : lista) {
                model.addRow(new Object[] {
                        jogo.getId(),
                        jogo.getDataHora(),
                        jogo.getLocal(),
                        jogo.getTimeA(),
                        jogo.getTimeB(),
//                        jogo.getFotoTimeA(),
//                        jogo.getFotoTimeB()
                });
            }

            table.getColumnModel().getColumn(0).setMinWidth(0);
            table.getColumnModel().getColumn(0).setMaxWidth(0);
            table.getColumnModel().getColumn(0).setWidth(0);

            labelResultados.setText("resultados: " + lista.size() + " jogos");
        } catch (Exception erro) {
            labelMensagem.setText(erro.getMessage());
        }
    }

    private void limparTudo() {
        textFieldDataHora.setText("");
        textFieldLocal.setText("");
        textFieldTimeA.setText("");
        textFieldTimeB.setText("");
        textFieldFotoTimeA.setText("");
        textFieldFotoTimeB.setText("");
        table.clearSelection();
        labelResultados.setText("resultados:");
        buttonDeletar.setEnabled(false);
        verificarCampos();
    }

    private void verificarCampos() {
        boolean todosCamposPreenchidos = !textFieldDataHora.getText().isEmpty() &&
                !textFieldLocal.getText().isEmpty() &&
                !textFieldTimeA.getText().isEmpty() &&
                !textFieldTimeB.getText().isEmpty() &&
                !textFieldFotoTimeA.getText().isEmpty() &&
                !textFieldFotoTimeB.getText().isEmpty();

        buttonCadastrar.setEnabled(todosCamposPreenchidos);
    }
}