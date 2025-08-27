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
    private JTextField textFieldNumero;
    private JButton buttonListar;
    private JButton buttonDeletar;
    private JButton buttonEditar;
    private JLabel labelMensagem;
    private JLabel labelNumero;
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
        frame.setBounds(100, 100, 729, 419);
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
                    textFieldNumero.setText(String.valueOf(numero));
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
        labelMensagem.setBounds(12, 355, 688, 14);
        frame.getContentPane().add(labelMensagem);

        labelResultados = new JLabel("resultados:");
        labelResultados.setBounds(21, 202, 431, 14);
        frame.getContentPane().add(labelResultados);

        labelNumero = new JLabel("Número:");
        labelNumero.setHorizontalAlignment(SwingConstants.LEFT);
        labelNumero.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelNumero.setBounds(21, 240, 60, 14);
        frame.getContentPane().add(labelNumero);

        textFieldNumero = new JTextField();
        textFieldNumero.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldNumero.setColumns(10);
        textFieldNumero.setBounds(91, 237, 100, 20);
        frame.getContentPane().add(textFieldNumero);


        buttonListar = new JButton("Listar");
        buttonListar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listagem();
            }
        });
        buttonListar.setBounds(308, 11, 89, 23);
        frame.getContentPane().add(buttonListar);

        buttonDeletar = new JButton("Deletar Selecionado");
        buttonDeletar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (table.getSelectedRow() >= 0) {
                        Long idCategoria = (Long) table.getValueAt(table.getSelectedRow(), 0);

                        Fachada.removerCategoria(idCategoria);
                        labelMensagem.setText("Categoria removida com sucesso");
                        listagem();
                        limparCampos();
                    } else {
                        labelMensagem.setText("Nenhuma categoria selecionada");
                    }
                } catch (Exception ex) {
                    labelMensagem.setText(ex.getMessage());
                }
            }
        });
        buttonDeletar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonDeletar.setBounds(21, 280, 171, 23);
        frame.getContentPane().add(buttonDeletar);

        buttonEditar = new JButton("Editar Número");
        buttonEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (table.getSelectedRow() >= 0) {
                        if (textFieldNumero.getText().isEmpty()) {
                            labelMensagem.setText("Número não pode estar vazio");
                            return;
                        }

                        Long idCategoria = (Long) table.getValueAt(table.getSelectedRow(), 0);
                        int novoNumero = Integer.parseInt(textFieldNumero.getText());

                        Fachada.alterarNumeroDaCategoria(novoNumero, idCategoria);
                        labelMensagem.setText("Número da categoria editado com sucesso");
                        listagem();
                        limparCampos();
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
        buttonEditar.setBounds(210, 280, 171, 23);
        frame.getContentPane().add(buttonEditar);
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

    private void limparCampos() {
        textFieldNumero.setText("");
    }
}