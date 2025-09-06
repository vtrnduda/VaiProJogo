package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import model.Categoria;
import requisito.Fachada;

public class TelaCategoria {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField textFieldNumeroEdicao;
    private JTextField textFieldNumeroCadastro;
    private JTextField textFieldPrecoCadastro;
    private JButton buttonListar;
    private JButton buttonDeletar;
    private JButton buttonEditar;
    private JButton buttonCadastrar;
    private JButton buttonLimpar;
    private JLabel labelMensagem;
    private JLabel labelNumeroEdicao;
    private JLabel labelNumeroCadastro;
    private JLabel labelPrecoCadastro;
    private JLabel labelResultados;

    public TelaCategoria() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("Categoria");
        frame.setBounds(100, 100, 729, 450);
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

        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 43, 674, 148);
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
                    int numero = (Integer) table.getValueAt(table.getSelectedRow(), 1);

                    labelResultados.setText("selecionado=" + id);
                    textFieldNumeroEdicao.setText(String.valueOf(numero));
                }
            }
        });
        table.setGridColor(Color.BLACK);
        table.setRequestFocusEnabled(false);
        table.setFocusable(false);
        table.setBackground(new Color(144, 238, 144));
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        labelMensagem = new JLabel("");
        labelMensagem.setForeground(Color.BLUE);
        labelMensagem.setBounds(12, 390, 688, 14);
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
        buttonListar.setBounds(308, 11, 89, 23);
        frame.getContentPane().add(buttonListar);

        labelNumeroEdicao = new JLabel("Número:");
        labelNumeroEdicao.setHorizontalAlignment(SwingConstants.LEFT);
        labelNumeroEdicao.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelNumeroEdicao.setBounds(21, 240, 60, 14);
        frame.getContentPane().add(labelNumeroEdicao);

        textFieldNumeroEdicao = new JTextField();
        textFieldNumeroEdicao.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldNumeroEdicao.setColumns(10);
        textFieldNumeroEdicao.setBounds(91, 237, 100, 20);
        frame.getContentPane().add(textFieldNumeroEdicao);

        buttonEditar = new JButton("Alterar Número");
        buttonEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (table.getSelectedRow() >= 0) {
                        if (textFieldNumeroEdicao.getText().isEmpty()) {
                            labelMensagem.setText("Número não pode estar vazio");
                            return;
                        }

                        Long idCategoria = (Long) table.getValueAt(table.getSelectedRow(), 0);
                        int novoNumero = Integer.parseInt(textFieldNumeroEdicao.getText());

                        Fachada.alterarNumeroDaCategoria(novoNumero, idCategoria);
                        labelMensagem.setText("Número da categoria editado com sucesso");
                        listagem();
                        limparTudo();
                    } else {
                        labelMensagem.setText("Nenhuma categoria selecionada");
                    }
                } catch (NumberFormatException ex) {
                    labelMensagem.setText("Número inválido");
                } catch (Exception ex) {
                    labelMensagem.setText(ex.getMessage());
                }
            }
        });
        buttonEditar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonEditar.setBounds(210, 236, 140, 23);
        frame.getContentPane().add(buttonEditar);

        labelNumeroCadastro = new JLabel("Número:");
        labelNumeroCadastro.setHorizontalAlignment(SwingConstants.LEFT);
        labelNumeroCadastro.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelNumeroCadastro.setBounds(21, 280, 60, 14);
        frame.getContentPane().add(labelNumeroCadastro);

        textFieldNumeroCadastro = new JTextField();
        textFieldNumeroCadastro.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldNumeroCadastro.setColumns(10);
        textFieldNumeroCadastro.setBounds(91, 277, 100, 20);
        frame.getContentPane().add(textFieldNumeroCadastro);

        labelPrecoCadastro = new JLabel("Preço:");
        labelPrecoCadastro.setHorizontalAlignment(SwingConstants.LEFT);
        labelPrecoCadastro.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelPrecoCadastro.setBounds(210, 280, 45, 14);
        frame.getContentPane().add(labelPrecoCadastro);

        textFieldPrecoCadastro = new JTextField();
        textFieldPrecoCadastro.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldPrecoCadastro.setColumns(10);
        textFieldPrecoCadastro.setBounds(265, 277, 100, 20);
        frame.getContentPane().add(textFieldPrecoCadastro);

        buttonCadastrar = new JButton("Cadastrar");
        buttonCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (textFieldNumeroCadastro.getText().isEmpty() || textFieldPrecoCadastro.getText().isEmpty()) {
                        labelMensagem.setText("Todos os campos devem ser preenchidos");
                        return;
                    }

                    int numero = Integer.parseInt(textFieldNumeroCadastro.getText());
                    double preco = Double.parseDouble(textFieldPrecoCadastro.getText());

                    Fachada.cadastrarCategoria(numero, preco);
                    labelMensagem.setText("Categoria cadastrada com sucesso");
                    listagem();
                    limparTudo();
                } catch (NumberFormatException ex) {
                    labelMensagem.setText("Número ou preço inválido");
                } catch (Exception ex) {
                    labelMensagem.setText(ex.getMessage());
                }
            }
        });
        buttonCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonCadastrar.setBounds(385, 276, 100, 23);
        frame.getContentPane().add(buttonCadastrar);

        buttonDeletar = new JButton("Deletar Selecionado");
        buttonDeletar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (table.getSelectedRow() >= 0) {
                        Long idCategoria = (Long) table.getValueAt(table.getSelectedRow(), 0);

                        Fachada.removerCategoria(idCategoria);
                        labelMensagem.setText("Categoria removida com sucesso");
                        listagem();
                        limparTudo();
                    } else {
                        labelMensagem.setText("Nenhuma categoria selecionada");
                    }
                } catch (Exception ex) {
                    labelMensagem.setText(ex.getMessage());
                }
            }
        });
        buttonDeletar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonDeletar.setBounds(21, 320, 170, 23);
        frame.getContentPane().add(buttonDeletar);

        buttonLimpar = new JButton("Limpar Tudo");
        buttonLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparTudo();
                labelMensagem.setText("Campos e seleção limpos");
            }
        });
        buttonLimpar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonLimpar.setBounds(202, 320, 120, 23);
        frame.getContentPane().add(buttonLimpar);
    }

    public void listagem() {
        try {
            List<Categoria> lista = Fachada.listarCategorias();

            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            model.addColumn("id");
            model.addColumn("numero");
            model.addColumn("preco");

            for (Categoria categoria : lista) {
                model.addRow(new Object[] {
                        categoria.getId(),
                        categoria.getNumero(),
                        categoria.getPreco()
                });
            }

            table.getColumnModel().getColumn(0).setMinWidth(0);
            table.getColumnModel().getColumn(0).setMaxWidth(0);
            table.getColumnModel().getColumn(0).setWidth(0);

            labelResultados.setText("resultados: " + lista.size() + " categorias");
        } catch (Exception erro) {
            labelMensagem.setText(erro.getMessage());
        }
    }

    private void limparTudo() {
        textFieldNumeroEdicao.setText("");
        textFieldNumeroCadastro.setText("");
        textFieldPrecoCadastro.setText("");
        table.clearSelection();
        labelResultados.setText("resultados:");
    }
}