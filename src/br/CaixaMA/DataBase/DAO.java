package br.CaixaMA.DataBase;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class DAO {

	ConnectionFactory conexao;

	public DAO(JFrame frame) {
		conexao = new ConnectionFactory();
		Connection con;
		try {
			con = conexao.getConnection();
			 if (con == null) {
			 JOptionPane
			 .showMessageDialog(frame,
			 "Não foi possivel se conectar ao banco de dados. Reinicie o programa. ");
			
			 }
		} catch (SQLException e) {
			JOptionPane
					.showMessageDialog(frame,
							"Não foi possivel se conectar ao banco de dados. Reinicie o programa.");
			JScrollPane scrollPane = new JScrollPane(new JLabel(e.getMessage()));
			scrollPane.setPreferredSize(new Dimension(200, 100));
			Object message = scrollPane;
			JOptionPane.showConfirmDialog(null, message,
					"dialog test with label", JOptionPane.YES_NO_OPTION);
			e.printStackTrace();
		}
	}

	public String[] getProduto(String codigo) {
		Connection con;
		String[] data = null;
		ResultSet rs = null;
		try {
			con = conexao.getConnection();
			PreparedStatement st = con
					.prepareStatement("SELECT nompro codbar preven FROM arqest WHERE codbar =  ? ");
			st.setString(1, codigo);
			st.execute();
			rs = st.getResultSet();
			rs.next();
			if (rs != null) {
				data = new String[3];
				data[0] = rs.getString("codbar");
				data[1] = rs.getString("nompro");
				data[2] = rs.getString("preven");
			}
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

}
