package appswing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.Categoria;
import model.Ingresso;
import model.Jogo;
import requisito.Fachada;

public class TelaIngresso {
    private JDialog frame;
    private JTable tableIngressos;
    private JTable tableJogos;
    private JTable tableCategorias;
    private JScrollPane scrollPaneIngressos;
    private JScrollPane scrollPaneJogos;
    private JScrollPane scrollPaneCategorias;
    private JTextField textFieldCodigo;
    private JTextField textFieldCodigoCategoria;

    private JButton buttonListar;
    private JButton buttonDeletar;
    private JButton buttonCadastrar;
    private JButton buttonLimpar;
    private JButton buttonAlterarCodigo;
    private JButton buttonAlterarCategoria;
    private JLabel labelMensagem;
    private JLabel labelCodigo;
    private JLabel labelResultadosIngressos;
    private JLabel labelResultadosJogos;
    private JLabel labelResultadosCategorias;

    private Long jogoSelecionadoId = null;
    private Integer categoriaSelecionadaNumero = null;

    public TelaIngresso() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("Ingresso");
        frame.setBounds(100, 100, 1200, 700);
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

        labelResultadosIngressos = new JLabel("Ingressos:");
        labelResultadosIngressos.setBounds(21, 12, 300, 14);
        labelResultadosIngressos.setFont(new Font("Tahoma", Font.BOLD, 12));
        frame.getContentPane().add(labelResultadosIngressos);

        scrollPaneIngressos = new JScrollPane();
        scrollPaneIngressos.setBounds(21, 30, 1150, 180);
        frame.getContentPane().add(scrollPaneIngressos);

        tableIngressos = new JTable() {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        };
        tableIngressos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (tableIngressos.getSelectedRow() >= 0) {
                    Long id = (Long) tableIngressos.getValueAt(tableIngressos.getSelectedRow(), 0);
                    String codigo = (String) tableIngressos.getValueAt(tableIngressos.getSelectedRow(), 1);

                    labelResultadosIngressos.setText("Ingresso selecionado: " + id);
                    textFieldCodigo.setText(codigo);
                    buttonDeletar.setEnabled(true);
                    buttonAlterarCodigo.setEnabled(true);
                    verificarSelecaoAlterarCategoria(); // Adicione esta linha
                }
            }
        });
        tableIngressos.setGridColor(Color.BLACK);
        tableIngressos.setRequestFocusEnabled(false);
        tableIngressos.setFocusable(false);
        tableIngressos.setBackground(new Color(144, 238, 144));
        tableIngressos.setFillsViewportHeight(true);
        tableIngressos.setRowSelectionAllowed(true);
        tableIngressos.setFont(new Font("Tahoma", Font.PLAIN, 12));
        scrollPaneIngressos.setViewportView(tableIngressos);
        tableIngressos.setBorder(new LineBorder(new Color(0, 0, 0)));
        tableIngressos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableIngressos.setShowGrid(true);
        tableIngressos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        labelResultadosJogos = new JLabel("Jogos:");
        labelResultadosJogos.setBounds(21, 230, 300, 14);
        labelResultadosJogos.setFont(new Font("Tahoma", Font.BOLD, 12));
        frame.getContentPane().add(labelResultadosJogos);

        scrollPaneJogos = new JScrollPane();
        scrollPaneJogos.setBounds(21, 250, 575, 150);
        frame.getContentPane().add(scrollPaneJogos);

        tableJogos = new JTable() {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        };
        tableJogos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (tableJogos.getSelectedRow() >= 0) {
                    Long id = (Long) tableJogos.getValueAt(tableJogos.getSelectedRow(), 0);
                    jogoSelecionadoId = id;
                    labelResultadosJogos.setText("Jogo selecionado: " + id);
                    verificarSelecoesCadastro();
                }
            }
        });
        tableJogos.setGridColor(Color.BLACK);
        tableJogos.setRequestFocusEnabled(false);
        tableJogos.setFocusable(false);
        tableJogos.setBackground(new Color(173, 216, 230));
        tableJogos.setFillsViewportHeight(true);
        tableJogos.setRowSelectionAllowed(true);
        tableJogos.setFont(new Font("Tahoma", Font.PLAIN, 10));
        tableJogos.setRowHeight(50);
        scrollPaneJogos.setViewportView(tableJogos);
        tableJogos.setBorder(new LineBorder(new Color(0, 0, 0)));
        tableJogos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableJogos.setShowGrid(true);
        tableJogos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        labelResultadosCategorias = new JLabel("Categorias:");
        labelResultadosCategorias.setBounds(620, 230, 300, 14);
        labelResultadosCategorias.setFont(new Font("Tahoma", Font.BOLD, 12));
        frame.getContentPane().add(labelResultadosCategorias);

        scrollPaneCategorias = new JScrollPane();
        scrollPaneCategorias.setBounds(620, 250, 550, 150);
        frame.getContentPane().add(scrollPaneCategorias);

        tableCategorias = new JTable() {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        };
        tableCategorias.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (tableCategorias.getSelectedRow() >= 0) {
                    Integer numero = (Integer) tableCategorias.getValueAt(tableCategorias.getSelectedRow(), 1);
                    categoriaSelecionadaNumero = numero;
                    labelResultadosCategorias.setText("Categoria selecionada: " + numero);
                    verificarSelecoesCadastro();
                }
            }
        });
        tableCategorias.setGridColor(Color.BLACK);
        tableCategorias.setRequestFocusEnabled(false);
        tableCategorias.setFocusable(false);
        tableCategorias.setBackground(new Color(255, 182, 193));
        tableCategorias.setFillsViewportHeight(true);
        tableCategorias.setRowSelectionAllowed(true);
        tableCategorias.setFont(new Font("Tahoma", Font.PLAIN, 12));
        scrollPaneCategorias.setViewportView(tableCategorias);
        tableCategorias.setBorder(new LineBorder(new Color(0, 0, 0)));
        tableCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableCategorias.setShowGrid(true);
        tableCategorias.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        buttonListar = new JButton("Listar Todos");
        buttonListar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listagem();
            }
        });
        buttonListar.setBounds(21, 420, 120, 23);
        frame.getContentPane().add(buttonListar);

        labelCodigo = new JLabel("Código:");
        labelCodigo.setHorizontalAlignment(SwingConstants.LEFT);
        labelCodigo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelCodigo.setBounds(21, 460, 60, 14);
        frame.getContentPane().add(labelCodigo);

        textFieldCodigo = new JTextField();
        textFieldCodigo.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldCodigo.setColumns(10);
        textFieldCodigo.setBounds(90, 457, 200, 20);
        frame.getContentPane().add(textFieldCodigo);

        buttonAlterarCodigo = new JButton("Alterar Código");
        buttonAlterarCodigo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tableIngressos.getSelectedRow() >= 0) {
                        if (textFieldCodigo.getText().isEmpty()) {
                            labelMensagem.setText("Código não pode estar vazio");
                            return;
                        }

                        Long idIngresso = (Long) tableIngressos.getValueAt(tableIngressos.getSelectedRow(), 0);
                        String novoCodigo = textFieldCodigo.getText();

                        Fachada.alterarCodigoDoIngresso(novoCodigo, idIngresso);
                        labelMensagem.setText("Código do ingresso alterado com sucesso");
                        listagem();
                        limparTudo();
                    } else {
                        labelMensagem.setText("Nenhum ingresso selecionado");
                    }
                } catch (Exception ex) {
                    labelMensagem.setText(ex.getMessage());
                }
            }
        });
        buttonAlterarCodigo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonAlterarCodigo.setBounds(300, 456, 140, 23);
        buttonAlterarCodigo.setEnabled(false);
        frame.getContentPane().add(buttonAlterarCodigo);

        buttonCadastrar = new JButton("Cadastrar Ingresso");
        buttonCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (jogoSelecionadoId == null) {
                        labelMensagem.setText("Selecione um jogo");
                        return;
                    }
                    if (categoriaSelecionadaNumero == null) {
                        labelMensagem.setText("Selecione uma categoria");
                        return;
                    }

                    Fachada.cadastrarIngresso(jogoSelecionadoId, categoriaSelecionadaNumero);
                    labelMensagem.setText("Ingresso cadastrado com sucesso");
                    listagem();
                    limparTudo();
                } catch (Exception ex) {
                    labelMensagem.setText(ex.getMessage());
                }
            }
        });
        buttonCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonCadastrar.setBounds(21, 500, 160, 23);
        buttonCadastrar.setEnabled(false);
        frame.getContentPane().add(buttonCadastrar);

        buttonDeletar = new JButton("Deletar Ingresso");
        buttonDeletar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tableIngressos.getSelectedRow() >= 0) {
                        Long idIngresso = (Long) tableIngressos.getValueAt(tableIngressos.getSelectedRow(), 0);

                        Fachada.removerIngresso(idIngresso);
                        labelMensagem.setText("Ingresso removido com sucesso");
                        listagem();
                        limparTudo();
                    } else {
                        labelMensagem.setText("Nenhum ingresso selecionado");
                    }
                } catch (Exception ex) {
                    labelMensagem.setText(ex.getMessage());
                }
            }
        });
        buttonDeletar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonDeletar.setBounds(200, 500, 150, 23);
        buttonDeletar.setEnabled(false);
        frame.getContentPane().add(buttonDeletar);

        buttonLimpar = new JButton("Limpar Seleções");
        buttonLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparTudo();
                labelMensagem.setText("Seleções e campos limpos");
            }
        });
        buttonLimpar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonLimpar.setBounds(370, 500, 150, 23);
        frame.getContentPane().add(buttonLimpar);

        labelMensagem = new JLabel("");
        labelMensagem.setForeground(Color.BLUE);
        labelMensagem.setBounds(21, 640, 1150, 14);
        frame.getContentPane().add(labelMensagem);

        // Label e campo para digitar o código da categoria
        JLabel labelCodigoCategoria = new JLabel("Código Categoria:");
        labelCodigoCategoria.setHorizontalAlignment(SwingConstants.LEFT);
        labelCodigoCategoria.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelCodigoCategoria.setBounds(420, 460, 120, 14);
        frame.getContentPane().add(labelCodigoCategoria);

        textFieldCodigoCategoria = new JTextField();
        textFieldCodigoCategoria.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldCodigoCategoria.setColumns(10);
        textFieldCodigoCategoria.setBounds(540, 457, 120, 20);
        frame.getContentPane().add(textFieldCodigoCategoria);

        // atualiza categoriaSelecionadaNumero quando o usuário digita (ou limpa)
        textFieldCodigoCategoria.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String txt = textFieldCodigoCategoria.getText().trim();
                if (txt.isEmpty()) {
                    categoriaSelecionadaNumero = null;
                } else {
                    try {
                        categoriaSelecionadaNumero = Integer.valueOf(txt);
                    } catch (NumberFormatException ex) {
                        categoriaSelecionadaNumero = null; // inválido enquanto digita
                    }
                }
                verificarSelecoesCadastro();
                verificarSelecaoAlterarCategoria();
            }
        });

        // Botão Alterar Categoria
        buttonAlterarCategoria = new JButton("Alterar Categoria");
        buttonAlterarCategoria.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tableIngressos.getSelectedRow() >= 0) {
                        String codigoIngresso = (String) tableIngressos.getValueAt(tableIngressos.getSelectedRow(), 1);

                        // Prioriza o valor digitado; se vazio, usa a seleção (categoriaSelecionadaNumero)
                        Long novaCategoriaId = null;
                        String txt = textFieldCodigoCategoria.getText().trim();
                        if (!txt.isEmpty()) {
                            try {
                                novaCategoriaId = Long.valueOf(txt);
                            } catch (NumberFormatException nfe) {
                                labelMensagem.setText("Código da categoria deve ser um número.");
                                return;
                            }
                        } else if (categoriaSelecionadaNumero != null) {
                            novaCategoriaId = Long.valueOf(categoriaSelecionadaNumero);
                        } else {
                            labelMensagem.setText("Informe ou selecione a categoria.");
                            return;
                        }

                        Fachada.alterarCategoriaDoIngresso(novaCategoriaId, codigoIngresso);
                        labelMensagem.setText("Categoria do ingresso alterada com sucesso");
                        listagem();
                        limparTudo();
                    } else {
                        labelMensagem.setText("Nenhum ingresso selecionado");
                    }
                } catch (Exception ex) {
                    labelMensagem.setText(ex.getMessage());
                }
            }
        });
        buttonAlterarCategoria.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonAlterarCategoria.setBounds(700, 456, 140, 23);
        buttonAlterarCategoria.setEnabled(false);
        frame.getContentPane().add(buttonAlterarCategoria);


    }

    private ImageIcon criarIconeImagem(byte[] dados) {
        try {
            if (dados != null && dados.length > 0) {
                BufferedImage imagem = ImageIO.read(new ByteArrayInputStream(dados));
                Image imagemRedimensionada = imagem.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                return new ImageIcon(imagemRedimensionada);
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    public void listagem() {
        listarIngressos();
        listarJogos();
        listarCategorias();
    }

    private void listarIngressos() {
        try {
            List<Ingresso> lista = Fachada.listarIngressos();

            DefaultTableModel model = new DefaultTableModel();
            tableIngressos.setModel(model);

            model.addColumn("ID");
            model.addColumn("Código");
            model.addColumn("Categoria");
            model.addColumn("Jogo");

            for (Ingresso ingresso : lista) {
                model.addRow(new Object[] {
                        ingresso.getId(),
                        ingresso.getCodigo(),
                        ingresso.getCategoria().getNumero(),
                        ingresso.getJogo().getId()
                });
            }

            labelResultadosIngressos.setText("Ingressos: " + lista.size() + " registros");
        } catch (Exception erro) {
            labelMensagem.setText(erro.getMessage());
        }
    }

    private void listarJogos() {
        try {
            List<Jogo> lista = Fachada.listarJogos();

            DefaultTableModel model = new DefaultTableModel();
            tableJogos.setModel(model);

            model.addColumn("ID");
            model.addColumn("Data/Hora");
            model.addColumn("Local");
            model.addColumn("Time A");
            model.addColumn("Foto A");
            model.addColumn("Time B");
            model.addColumn("Foto B");

            for (Jogo jogo : lista) {
                ImageIcon iconTimeA = criarIconeImagem(jogo.getFotoTimeA());
                ImageIcon iconTimeB = criarIconeImagem(jogo.getFotoTimeB());

                model.addRow(new Object[] {
                        jogo.getId(),
                        jogo.getDataHora(),
                        jogo.getLocal(),
                        jogo.getTimeA(),
                        iconTimeA != null ? iconTimeA : "Sem foto",
                        jogo.getTimeB(),
                        iconTimeB != null ? iconTimeB : "Sem foto"
                });
            }

            tableJogos.getColumnModel().getColumn(0).setMinWidth(30);
            tableJogos.getColumnModel().getColumn(0).setMaxWidth(50);

            tableJogos.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                               boolean hasFocus, int row, int column) {
                    if (value instanceof ImageIcon) {
                        setIcon((ImageIcon) value);
                        setText("");
                    } else {
                        setIcon(null);
                        setText(value != null ? value.toString() : "");
                    }
                    setHorizontalAlignment(SwingConstants.CENTER);
                    return this;
                }
            });

            tableJogos.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                               boolean hasFocus, int row, int column) {
                    if (value instanceof ImageIcon) {
                        setIcon((ImageIcon) value);
                        setText("");
                    } else {
                        setIcon(null);
                        setText(value != null ? value.toString() : "");
                    }
                    setHorizontalAlignment(SwingConstants.CENTER);
                    return this;
                }
            });

            labelResultadosJogos.setText("Jogos: " + lista.size() + " registros");
        } catch (Exception erro) {
            labelMensagem.setText(erro.getMessage());
        }
    }

    private void listarCategorias() {
        try {
            List<Categoria> lista = Fachada.listarCategorias();

            DefaultTableModel model = new DefaultTableModel();
            tableCategorias.setModel(model);

            model.addColumn("ID");
            model.addColumn("Número");
            model.addColumn("Preço");

            for (Categoria categoria : lista) {
                model.addRow(new Object[] {
                        categoria.getId(),
                        categoria.getNumero(),
                        "R$ " + String.format("%.2f", categoria.getPreco())
                });
            }

            tableCategorias.getColumnModel().getColumn(0).setMinWidth(0);
            tableCategorias.getColumnModel().getColumn(0).setMaxWidth(0);
            tableCategorias.getColumnModel().getColumn(0).setWidth(0);

            labelResultadosCategorias.setText("Categorias: " + lista.size() + " registros");
        } catch (Exception erro) {
            labelMensagem.setText(erro.getMessage());
        }
    }


    private void limparTudo() {
        textFieldCodigo.setText("");
        textFieldCodigoCategoria.setText(""); // Adicione esta linha

        tableIngressos.clearSelection();
        tableJogos.clearSelection();
        tableCategorias.clearSelection();

        jogoSelecionadoId = null;
        categoriaSelecionadaNumero = null;

        buttonDeletar.setEnabled(false);
        buttonAlterarCodigo.setEnabled(false);
        buttonCadastrar.setEnabled(false);
        buttonAlterarCategoria.setEnabled(false); // Adicione esta linha

        labelResultadosIngressos.setText("Ingressos:");
        labelResultadosJogos.setText("Jogos:");
        labelResultadosCategorias.setText("Categorias:");
    }

    private void verificarSelecoesCadastro() {
        boolean podesCadastrar = jogoSelecionadoId != null && categoriaSelecionadaNumero != null;
        buttonCadastrar.setEnabled(podesCadastrar);
    }

    private void verificarSelecaoAlterarCategoria() {
        // Só habilita se tem ingresso selecionado
        boolean temIngressoSelecionado = tableIngressos.getSelectedRow() >= 0;
        buttonAlterarCategoria.setEnabled(temIngressoSelecionado);
    }
}