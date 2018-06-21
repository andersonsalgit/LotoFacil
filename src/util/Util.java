package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConexaoBD;

public class Util {

	private static Connection conn;
	
	public static void alterTableToCopyNewTable() {
		
		System.out.println(" \n ");
		System.out.println(" Entrou para alterar as combinações.");
		System.out.println(" \n ");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//Verifica se já existe a combinação no banco
		//String query = "SELECT * FROM public.tb_loto_combinacoes_15 ORDER BY id ASC LIMIT 10";//query inicial
		String query = "SELECT * FROM public.tb_loto_combinacoes_15 WHERE id > 3268761 ORDER BY id ASC LIMIT 500000";//query com os intervalos iniciar próxima linha '+1'
		
		
		System.out.println("query: "+query);
		
		try {
			
			conn = ConexaoBD.getInstance();
			
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			int linhaExecID = 0; 
			
			while (rs.next()) {
				
				System.out.println(rs.getInt("id")+ " - "+rs.getString("combinacao"));
			
				linhaExecID = rs.getInt("id");
				
				insertNewTableDb(rs.getInt("id"), rs.getString("combinacao"), ps);
				
			}
			
			createLogLinhaExec(linhaExecID);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finalizar(ps, rs);
		
	}
	
	private static void createLogLinhaExec(int linhaExecID) {
		
		FileWriter arq;
		try {
			
			arq = new FileWriter("logLine.txt");
			
			PrintWriter gravarArq = new PrintWriter(arq);
			
			gravarArq.print("\n");
		    gravarArq.print("Ultimo linha executada: \t"+String.valueOf(linhaExecID-1));
		    gravarArq.print("\n");
		    gravarArq.print("Próximo linha começar em: \t"+linhaExecID);
		 
		    arq.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	public static void insertNewTableDb(int id, String combinacao, PreparedStatement ps) {
		
		
		String[] sep = combinacao.split(",");
		
		String query = "INSERT INTO public.tb_combinacoes_possiveis_15(id, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";		
		try {
			
			conn = ConexaoBD.getInstance();
			
			ps = conn.prepareStatement(query);
			ps.setInt(1, id-1);
			ps.setInt(2, Integer.parseInt(sep[0]));
			ps.setInt(3, Integer.parseInt(sep[1]));
			ps.setInt(4, Integer.parseInt(sep[2]));
			ps.setInt(5, Integer.parseInt(sep[3]));
			ps.setInt(6, Integer.parseInt(sep[4]));
			ps.setInt(7, Integer.parseInt(sep[5]));
			ps.setInt(8, Integer.parseInt(sep[6]));
			ps.setInt(9, Integer.parseInt(sep[7]));
			ps.setInt(10, Integer.parseInt(sep[8]));
			ps.setInt(11, Integer.parseInt(sep[9]));
			ps.setInt(12, Integer.parseInt(sep[10]));
			ps.setInt(13, Integer.parseInt(sep[11]));
			ps.setInt(14, Integer.parseInt(sep[12]));
			ps.setInt(15, Integer.parseInt(sep[13]));
			ps.setInt(16, Integer.parseInt(sep[14]));
			
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void finalizar(PreparedStatement ps, ResultSet rs) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException sqe) {
				System.out.println("Erro ao fechar PS" + sqe.getMessage());
			} catch (RuntimeException re) {
				System.out.println("Erro ao fechar PS" + re.getMessage());
			}
		}

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException sqe) {
				System.out.println("Erro ao fechar ResultSet"
						+ sqe.getMessage());
			} catch (RuntimeException re) {
				System.out
						.println("Erro ao fechar ResultSet" + re.getMessage());
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException sqe) {
				System.out.println("Erro ao fechar CONN" + sqe.getMessage());
			} catch (RuntimeException re) {
				System.out.println("Erro ao fechar CONN" + re.getMessage());
			}
		}
	}
	
}
