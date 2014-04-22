package br.CaixaMA.App;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.CaixaMA.DataBase.DAO;
import br.CaixaMA.Utils.FixedLengthDocument;

public class CaixaMA {

	private JFrame frame;
	private JTextField tf_codigo, tf_total;
	private JScrollPane scrollPane;
	private DefaultTableModel modelo;
	private DAO dao;
	private JTable tabela;
	private JTextField tf_Qtd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CaixaMA window = new CaixaMA();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CaixaMA() {
		initialize();
		dao = new DAO(frame);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);

		JLabel lblCodigoDoProduto = new JLabel("Codigo do Produto");
		lblCodigoDoProduto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCodigoDoProduto.setBounds(10, 33, 184, 25);
		frame.getContentPane().add(lblCodigoDoProduto);

		JLabel lblQuantidade = new JLabel("Quantidade");
		lblQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblQuantidade.setBounds(10, 147, 184, 25);
		frame.getContentPane().add(lblQuantidade);

		JLabel lblTotal = new JLabel("TOTAL");
		lblTotal.setBounds(493, 466, 73, 55);
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		frame.getContentPane().add(lblTotal);

		tf_total = new JTextField("0.00");
		tf_total.setEditable(false);
		tf_total.setBounds(576, 466, 175, 55);
		tf_total.setFont(new Font("Tahoma", Font.PLAIN, 54));
		frame.getContentPane().add(tf_total);

		tf_codigo = new JTextField();
		tf_codigo.setFont(new Font("Tahoma", Font.PLAIN, 36));
		tf_codigo.setDocument(new FixedLengthDocument(13));
		tf_codigo.setBounds(10, 70, 269, 66);
		tf_codigo.setColumns(10);
		tf_codigo.requestFocus();
		tf_codigo.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent key) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent key) {
//				System.out.println(key.getKeyCode());
//				if (key.getKeyCode() == KeyEvent.VK_ENTER) {
//
//					addRow();
//				}
//				if (key.getKeyCode() == KeyEvent.VK_F6) {
//					novo();
//				}
//				// KeyEvent.VK_ASTERISK

				switch (key.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					addRow();
					break;
				case KeyEvent.VK_F6:
					novo();
					break;
//				case KeyEvent.VK_ASTERISK:
//					tf_Qtd.setText("");
//					tf_Qtd.requestFocus();
//					break;
				default:
					break;
				}
			}

		});

		tf_Qtd = new JTextField();
		tf_Qtd.setFont(new Font("Tahoma", Font.PLAIN, 56));
		tf_Qtd.setText("1");
		tf_Qtd.setBounds(10, 176, 86, 66);
		frame.getContentPane().add(tf_Qtd);
		tf_Qtd.setColumns(10);
		frame.getContentPane().add(tf_codigo);

		modelo = new DefaultTableModel();
		modelo.addColumn("Codigo");
		modelo.addColumn("Produto");
		modelo.addColumn("Quantidade");
		modelo.addColumn("Preço");
		modelo.addColumn("Total");

		tabela = new JTable(modelo);
		tabela.setEnabled(false);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(10);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(10);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(10);

		scrollPane = new JScrollPane(tabela);
		scrollPane.setBounds(289, 11, 462, 435);
		frame.getContentPane().add(scrollPane);

	}

	public void novo() {
		tf_codigo.requestFocus();
		tf_Qtd.setText("1");
		tf_codigo.setText("");
		tf_total.setText("");
		for (int i = modelo.getRowCount() - 1; i >= 0; i--) {
			modelo.removeRow(i);
		}
	}

	public void addRow() {
		try {
			String[] data = dao.getProduto(tf_codigo.getText().trim());

			double total = Integer.parseInt(tf_Qtd.getText().trim())
					* Double.parseDouble(data[2]);
			double totalFinal = converterDoubleDoisDecimais(total);
			modelo.addRow(new Object[] { data[0], data[1],
					tf_Qtd.getText().trim(), data[2], totalFinal });
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Produto não encontrado");
		}
		tf_codigo.setText("");
		tf_Qtd.setText("1");
		double soma = 0.0;
		for (int i = 0; i < modelo.getRowCount(); i++) {
			soma = soma
					+ Double.parseDouble(tabela.getValueAt(i, 4).toString());
		}
		soma = converterDoubleDoisDecimais(soma);
		tf_total.setText(soma + "");
		tf_codigo.requestFocus();
	}

	public static double converterDoubleDoisDecimais(double precoDouble) {
		DecimalFormat fmt = new DecimalFormat("0.00");
		String string = fmt.format(precoDouble);
		String[] part = string.split("[,]");
		String string2 = part[0] + "." + part[1];
		double preco = Double.parseDouble(string2);
		return preco;
	}
}
