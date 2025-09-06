package appswing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class TelaConsulta {
    private JDialog frame;
    private JTable tableResultados;
    private JScrollPane scrollPaneResultados;
    private JComboBox<String> comboBoxConsultas;
    private JTextField textFieldPreco;
    private JTextField textFieldNumeroCategoria;
    private JTextField textFieldIdJogo;
    private JTextField textFieldQtdIngressos;
    private JButton buttonConsultar;
    private JButton buttonLimpar;
    private JLabel labelMensagem;
    private JLabel labelPreco;
    private JLabel labelNumeroCategoria;
    private JLabel labelIdJogo;
    private JLabel labelQtdIngressos;
    private JLabel labelResultados;

    private String[] opcoeConsultas = {
            "Selecione uma consulta...",
            "Categorias com preço maior que",
            "Ingressos da categoria X do jogo Y",
            "Jogos com mais de X ingressos vendidos"
    };

    public TelaConsulta() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("Consultas");
        frame.setBounds(100, 100, 1000, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                Fachada.inicializar();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Fachada.finalizar();
            }
        });

        JLabel labelTipoConsulta = new JLabel("Tipo de consulta:");
        labelTipoConsulta.setFont(new Font("Tahoma", Font.BOLD, 12));
        labelTipoConsulta.setBounds(21, 20, 120, 14);
        frame.getContentPane().add(labelTipoConsulta);

        comboBoxConsultas = new JComboBox<>(opcoeConsultas);
        comboBoxConsultas.setBounds(150, 17, 300, 20);
        comboBoxConsultas.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    configurarCamposPorConsulta();
                }
            }
        });
        frame.getContentPane().add(comboBoxConsultas);

        labelPreco = new JLabel("Preço:");
        labelPreco.setHorizontalAlignment(SwingConstants.LEFT);
        labelPreco.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelPreco.setBounds(21, 70, 60, 14);
        labelPreco.setVisible(false);
        frame.getContentPane().add(labelPreco);

        textFieldPreco = new JTextField();
        textFieldPreco.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldPreco.setBounds(90, 67, 100, 20);
        textFieldPreco.setVisible(false);
        frame.getContentPane().add(textFieldPreco);

        labelNumeroCategoria = new JLabel("Número da Categoria:");
        labelNumeroCategoria.setHorizontalAlignment(SwingConstants.LEFT);
        labelNumeroCategoria.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelNumeroCategoria.setBounds(21, 70, 140, 14);
        labelNumeroCategoria.setVisible(false);
        frame.getContentPane().add(labelNumeroCategoria);

        textFieldNumeroCategoria = new JTextField();
        textFieldNumeroCategoria.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldNumeroCategoria.setBounds(170, 67, 100, 20);
        textFieldNumeroCategoria.setVisible(false);
        frame.getContentPane().add(textFieldNumeroCategoria);

        labelIdJogo = new JLabel("ID do Jogo:");
        labelIdJogo.setHorizontalAlignment(SwingConstants.LEFT);
        labelIdJogo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelIdJogo.setBounds(290, 70, 80, 14);
        labelIdJogo.setVisible(false);
        frame.getContentPane().add(labelIdJogo);

        textFieldIdJogo = new JTextField();
        textFieldIdJogo.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldIdJogo.setBounds(380, 67, 100, 20);
        textFieldIdJogo.setVisible(false);
        frame.getContentPane().add(textFieldIdJogo);

        labelQtdIngressos = new JLabel("Quantidade de Ingressos:");
        labelQtdIngressos.setHorizontalAlignment(SwingConstants.LEFT);
        labelQtdIngressos.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelQtdIngressos.setBounds(21, 70, 169, 14);
        labelQtdIngressos.setVisible(false);
        frame.getContentPane().add(labelQtdIngressos);

        textFieldQtdIngressos = new JTextField();
        textFieldQtdIngressos.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldQtdIngressos.setBounds(190, 67, 100, 20);
        textFieldQtdIngressos.setVisible(false);
        frame.getContentPane().add(textFieldQtdIngressos);

        buttonConsultar = new JButton("Consultar");
        buttonConsultar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonConsultar.setBounds(21, 110, 120, 23);
        buttonConsultar.setEnabled(false);
        buttonConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                executarConsulta();
            }
        });
        frame.getContentPane().add(buttonConsultar);

        buttonLimpar = new JButton("Limpar");
        buttonLimpar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonLimpar.setBounds(160, 110, 100, 23);
        buttonLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparTudo();
                labelMensagem.setText("Campos limpos");
            }
        });
        frame.getContentPane().add(buttonLimpar);

        labelResultados = new JLabel("Resultados:");
        labelResultados.setBounds(21, 150, 400, 14);
        labelResultados.setFont(new Font("Tahoma", Font.BOLD, 12));
        frame.getContentPane().add(labelResultados);

        scrollPaneResultados = new JScrollPane();
        scrollPaneResultados.setBounds(21, 170, 950, 350);
        frame.getContentPane().add(scrollPaneResultados);

        tableResultados = new JTable() {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        };
        tableResultados.setGridColor(Color.BLACK);
        tableResultados.setRequestFocusEnabled(false);
        tableResultados.setFocusable(false);
        tableResultados.setBackground(new Color(255, 255, 224));
        tableResultados.setFillsViewportHeight(true);
        tableResultados.setRowSelectionAllowed(true);
        tableResultados.setFont(new Font("Tahoma", Font.PLAIN, 12));
        scrollPaneResultados.setViewportView(tableResultados);
        tableResultados.setBorder(new LineBorder(new Color(0, 0, 0)));
        tableResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableResultados.setShowGrid(true);
        tableResultados.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        labelMensagem = new JLabel("");
        labelMensagem.setForeground(Color.BLUE);
        labelMensagem.setBounds(21, 540, 950, 14);
        frame.getContentPane().add(labelMensagem);
    }

    private void configurarCamposPorConsulta() {
        ocultarTodosCampos();

        int indiceConsulta = comboBoxConsultas.getSelectedIndex();

        switch(indiceConsulta) {
            case 1:
                labelPreco.setVisible(true);
                textFieldPreco.setVisible(true);
                buttonConsultar.setEnabled(true);
                break;

            case 2:
                labelNumeroCategoria.setVisible(true);
                textFieldNumeroCategoria.setVisible(true);
                labelIdJogo.setVisible(true);
                textFieldIdJogo.setVisible(true);
                buttonConsultar.setEnabled(true);
                break;

            case 3:
                labelQtdIngressos.setVisible(true);
                textFieldQtdIngressos.setVisible(true);
                buttonConsultar.setEnabled(true);
                break;

            default:
                buttonConsultar.setEnabled(false);
                break;
        }

        limparResultados();
    }

    private void ocultarTodosCampos() {
        labelPreco.setVisible(false);
        textFieldPreco.setVisible(false);
        labelNumeroCategoria.setVisible(false);
        textFieldNumeroCategoria.setVisible(false);
        labelIdJogo.setVisible(false);
        textFieldIdJogo.setVisible(false);
        labelQtdIngressos.setVisible(false);
        textFieldQtdIngressos.setVisible(false);
    }

    private void executarConsulta() {
        try {
            int indiceConsulta = comboBoxConsultas.getSelectedIndex();

            switch(indiceConsulta) {
                case 1:
                    consultarCategoriasPorPreco();
                    break;
                case 2:
                    consultarIngressosPorCategoriaEJogo();
                    break;
                case 3:
                    consultarJogosPorQuantidadeIngressos();
                    break;
            }
        } catch (NumberFormatException ex) {
            labelMensagem.setText("Valor numérico inválido");
        } catch (Exception ex) {
            labelMensagem.setText(ex.getMessage());
        }
    }

    private void consultarCategoriasPorPreco() {
        if (textFieldPreco.getText().isEmpty()) {
            labelMensagem.setText("Informe o preço");
            return;
        }

        double preco = Double.parseDouble(textFieldPreco.getText());
        List<Categoria> categorias = Fachada.categoriasComPrecoMaiorQue(preco);

        DefaultTableModel model = new DefaultTableModel();
        tableResultados.setModel(model);

        model.addColumn("ID");
        model.addColumn("Número");
        model.addColumn("Preço");

        for (Categoria categoria : categorias) {
            model.addRow(new Object[] {
                    categoria.getId(),
                    categoria.getNumero(),
                    "R$ " + String.format("%.2f", categoria.getPreco())
            });
        }

        labelResultados.setText("Categorias com preço maior que R$ " + String.format("%.2f", preco) + ": " + categorias.size() + " registros");
        labelMensagem.setText("Consulta executada com sucesso");
    }

    private void consultarIngressosPorCategoriaEJogo() {
        if (textFieldNumeroCategoria.getText().isEmpty() || textFieldIdJogo.getText().isEmpty()) {
            labelMensagem.setText("Informe o número da categoria e o ID do jogo");
            return;
        }

        int numeroCategoria = Integer.parseInt(textFieldNumeroCategoria.getText());
        Long idJogo = Long.parseLong(textFieldIdJogo.getText());

        List<Ingresso> ingressos = Fachada.ingressosDaCategoriaXDoJogoY(numeroCategoria, idJogo);

        DefaultTableModel model = new DefaultTableModel();
        tableResultados.setModel(model);

        model.addColumn("ID");
        model.addColumn("Código");
        model.addColumn("Categoria");
        model.addColumn("Jogo");

        for (Ingresso ingresso : ingressos) {
            model.addRow(new Object[] {
                    ingresso.getId(),
                    ingresso.getCodigo(),
                    ingresso.getCategoria().getNumero(),
                    ingresso.getJogo().getId()
            });
        }

        labelResultados.setText("Ingressos da categoria " + numeroCategoria + " do jogo " + idJogo + ": " + ingressos.size() + " registros");
        labelMensagem.setText("Consulta executada com sucesso");
    }

    private void consultarJogosPorQuantidadeIngressos() {
        if (textFieldQtdIngressos.getText().isEmpty()) {
            labelMensagem.setText("Informe a quantidade de ingressos");
            return;
        }

        int qtdIngressos = Integer.parseInt(textFieldQtdIngressos.getText());
        List<Jogo> jogos = Fachada.jogosComMaisDeXIngressosVendidos(qtdIngressos);

        DefaultTableModel model = new DefaultTableModel();
        tableResultados.setModel(model);
        tableResultados.setRowHeight(50);

        model.addColumn("ID");
        model.addColumn("Data/Hora");
        model.addColumn("Local");
        model.addColumn("Time A");
        model.addColumn("Foto A");
        model.addColumn("Time B");
        model.addColumn("Foto B");

        for (Jogo jogo : jogos) {
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

        configurarRenderizadorImagens();

        labelResultados.setText("Jogos com mais de " + qtdIngressos + " ingressos: " + jogos.size() + " registros");
        labelMensagem.setText("Consulta executada com sucesso");
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

    private void configurarRenderizadorImagens() {
        if (tableResultados.getColumnCount() > 4) {
            tableResultados.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
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
        }

        if (tableResultados.getColumnCount() > 6) {
            tableResultados.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
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
        }
    }

    private void limparTudo() {
        textFieldPreco.setText("");
        textFieldNumeroCategoria.setText("");
        textFieldIdJogo.setText("");
        textFieldQtdIngressos.setText("");
        limparResultados();
        comboBoxConsultas.setSelectedIndex(0);
    }

    private void limparResultados() {
        DefaultTableModel model = new DefaultTableModel();
        tableResultados.setModel(model);
        tableResultados.setRowHeight(23);
        labelResultados.setText("Resultados:");
    }
}