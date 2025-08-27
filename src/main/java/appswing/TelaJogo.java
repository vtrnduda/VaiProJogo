package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    private JPanel panelFotoTimeA;
    private JPanel panelFotoTimeB;
    private JLabel labelFotoTimeAPath;
    private JLabel labelFotoTimeBPath;
    private JButton buttonListar;
    private JButton buttonDeletar;
    private JButton buttonCadastrar;
    private JButton buttonLimpar;
    private JButton buttonEscolherFotoA;
    private JButton buttonEscolherFotoB;
    private JLabel labelMensagem;
    private JLabel labelDataHora;
    private JLabel labelLocal;
    private JLabel labelTimeA;
    private JLabel labelTimeB;
    private JLabel labelFotoTimeA;
    private JLabel labelFotoTimeB;
    private JLabel labelResultados;

    private byte[] fotoTimeA;
    private byte[] fotoTimeB;

    public TelaJogo() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("Jogo");
        frame.setBounds(100, 100, 850, 580);
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
        labelMensagem.setBounds(12, 530, 800, 14);
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

        panelFotoTimeA = new JPanel();
        panelFotoTimeA.setBounds(21, 340, 200, 60);
        panelFotoTimeA.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
        panelFotoTimeA.setBackground(Color.WHITE);
        panelFotoTimeA.setLayout(null);
        configurarDropTarget(panelFotoTimeA, true);
        frame.getContentPane().add(panelFotoTimeA);

        labelFotoTimeAPath = new JLabel("Arraste uma imagem aqui");
        labelFotoTimeAPath.setBounds(10, 5, 180, 50);
        labelFotoTimeAPath.setFont(new Font("Tahoma", Font.PLAIN, 10));
        labelFotoTimeAPath.setHorizontalAlignment(SwingConstants.CENTER);
        labelFotoTimeAPath.setVerticalAlignment(SwingConstants.CENTER);
        panelFotoTimeA.add(labelFotoTimeAPath);

        buttonEscolherFotoA = new JButton("Escolher");
        buttonEscolherFotoA.setBounds(230, 350, 80, 25);
        buttonEscolherFotoA.setFont(new Font("Tahoma", Font.PLAIN, 10));
        buttonEscolherFotoA.addActionListener(e -> {
            try {
                escolherImagem(true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        frame.getContentPane().add(buttonEscolherFotoA);

        labelFotoTimeB = new JLabel("Foto Time B:");
        labelFotoTimeB.setHorizontalAlignment(SwingConstants.LEFT);
        labelFotoTimeB.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelFotoTimeB.setBounds(420, 320, 80, 14);
        frame.getContentPane().add(labelFotoTimeB);

        panelFotoTimeB = new JPanel();
        panelFotoTimeB.setBounds(420, 340, 200, 60);
        panelFotoTimeB.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
        panelFotoTimeB.setBackground(Color.WHITE);
        panelFotoTimeB.setLayout(null);
        configurarDropTarget(panelFotoTimeB, false);
        frame.getContentPane().add(panelFotoTimeB);

        labelFotoTimeBPath = new JLabel("Arraste uma imagem aqui");
        labelFotoTimeBPath.setBounds(10, 5, 180, 50);
        labelFotoTimeBPath.setFont(new Font("Tahoma", Font.PLAIN, 10));
        labelFotoTimeBPath.setHorizontalAlignment(SwingConstants.CENTER);
        labelFotoTimeBPath.setVerticalAlignment(SwingConstants.CENTER);
        panelFotoTimeB.add(labelFotoTimeBPath);

        buttonEscolherFotoB = new JButton("Escolher");
        buttonEscolherFotoB.setBounds(630, 350, 80, 25);
        buttonEscolherFotoB.setFont(new Font("Tahoma", Font.PLAIN, 10));
        buttonEscolherFotoB.addActionListener(e -> {
            try {
                escolherImagem(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        frame.getContentPane().add(buttonEscolherFotoB);

        buttonCadastrar = new JButton("Cadastrar");
        buttonCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String dataHoraTexto = textFieldDataHora.getText();
                    String local = textFieldLocal.getText();
                    String timeA = textFieldTimeA.getText();
                    String timeB = textFieldTimeB.getText();

                    String[] partesDataHora = dataHoraTexto.split(" ");
                    if (partesDataHora.length != 2) {
                        throw new RuntimeException("Formato inválido. Use: dd/MM/yyyy HH:mm");
                    }

                    DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

                    LocalDate data = LocalDate.parse(partesDataHora[0], formatoData);
                    LocalTime hora = LocalTime.parse(partesDataHora[1], formatoHora);

                    Fachada.cadastrarJogo(data, hora, local, timeA, timeB, fotoTimeA, fotoTimeB);
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
        buttonCadastrar.setBounds(21, 420, 120, 23);
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
        buttonDeletar.setBounds(160, 420, 170, 23);
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
        buttonLimpar.setBounds(350, 420, 120, 23);
        frame.getContentPane().add(buttonLimpar);
    }

    private void configurarDropTarget(JPanel panel, boolean isTimeA) {
        new DropTarget(panel, new java.awt.dnd.DropTargetListener() {
            @Override
            public void dragEnter(java.awt.dnd.DropTargetDragEvent dtde) {
                panel.setBackground(new Color(230, 230, 255));
            }

            @Override
            public void dragOver(java.awt.dnd.DropTargetDragEvent dtde) {}

            @Override
            public void dropActionChanged(java.awt.dnd.DropTargetDragEvent dtde) {}

            @Override
            public void dragExit(java.awt.dnd.DropTargetEvent dte) {
                panel.setBackground(Color.WHITE);
            }

            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    Transferable transferable = dtde.getTransferable();

                    if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

                        if (!files.isEmpty()) {
                            File file = files.get(0);
                            String fileName = file.getName().toLowerCase();

                            if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
                                    fileName.endsWith(".png") || fileName.endsWith(".gif")) {

                                if (isTimeA) {
                                    String caminhoFotoTimeA = file.getAbsolutePath();
                                    fotoTimeA = Files.readAllBytes(Paths.get(caminhoFotoTimeA));
                                    labelFotoTimeAPath.setText(file.getName());
                                } else {
                                    String caminhoFotoTimeB = file.getAbsolutePath();
                                    fotoTimeB = Files.readAllBytes(Paths.get(caminhoFotoTimeB));
                                    labelFotoTimeBPath.setText(file.getName());
                                }
                                verificarCampos();
                            } else {
                                labelMensagem.setText("Por favor, selecione um arquivo de imagem válido");
                            }
                        }
                    }
                    panel.setBackground(Color.WHITE);
                } catch (Exception e) {
                    labelMensagem.setText("Erro ao processar arquivo: " + e.getMessage());
                    panel.setBackground(Color.WHITE);
                }
            }
        });
    }

    private void escolherImagem(boolean isTimeA) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Arquivos de Imagem", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            if (isTimeA) {
                String caminhoFotoTimeA = file.getAbsolutePath();
                fotoTimeA = Files.readAllBytes(Paths.get(caminhoFotoTimeA));
                labelFotoTimeAPath.setText(file.getName());
            } else {
                String caminhoFotoTimeB = file.getAbsolutePath();
                fotoTimeB = Files.readAllBytes(Paths.get(caminhoFotoTimeB));
                labelFotoTimeBPath.setText(file.getName());
            }
            verificarCampos();
        }
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

            for (Jogo jogo : lista) {
                model.addRow(new Object[] {
                        jogo.getId(),
                        jogo.getDataHora(),
                        jogo.getLocal(),
                        jogo.getTimeA(),
                        jogo.getTimeB()
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

        fotoTimeA = null;
        fotoTimeB = null;
        labelFotoTimeAPath.setText("Arraste uma imagem aqui");
        labelFotoTimeBPath.setText("Arraste uma imagem aqui");

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
                fotoTimeA != null && fotoTimeA.length > 0 &&
                fotoTimeB != null && fotoTimeB.length > 0;

        buttonCadastrar.setEnabled(todosCamposPreenchidos);
    }
}