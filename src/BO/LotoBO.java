package BO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import connection.ConexaoBD;

public class LotoBO implements Runnable{
	
private Connection conn;
	
	static int indexList;

	private List<List<Integer>> listCombinados;
	private int combinacoes;
	private int corte;

	public LotoBO() {}
	
	public LotoBO(List<List<Integer>> listCombinados, int combinacoes, int corte) {
		this.listCombinados = listCombinados;
		this.combinacoes = combinacoes;
		this.corte = corte;
	}

	@Override
	public void run() {
		
		//Tempo inicial da execução
		Instant startTimeSalvo = Instant.now();
		
		salvaCombinacoes(listCombinados, combinacoes, corte);
		
		// faz o trabalho a ser medido tempo final
        Instant endTimeSalvo = Instant.now();
        Duration totalTime = Duration.between(startTimeSalvo, endTimeSalvo);
        
        System.out.println("--------------Salvo no banco--------------");
        System.out.println("Em: "+totalTime.getSeconds()+" segundos.");
        System.out.println("Tamanho da lista: "+listCombinados.size());
        
	}
	
	public void salvaCombinacoes(List<List<Integer>> listCombinados, int combinacoes, int corte) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//Monta ? da query
		String values = "(?),";
		for (int i = 1; i < corte; i++) {
			values += "(?),";
		}
		
		values = values.substring(0, values.length() -1);
		
		//Verifica se já existe a combinação no banco
		String query = "INSERT INTO tb_teste (combinacao) VALUES "+values;		
		try {
			
			this.conn = ConexaoBD.getInstance();
			
			ps = conn.prepareStatement(query);
			
			int index = 1;
			for (int i = 0; i < corte; i++) {
				
				String combinadoStr = listCombinados.get(indexList).toString().replace("[", "");
				combinadoStr = combinadoStr.replace("]", "");
				combinadoStr = combinadoStr.replace(" ", "");
				
				ps.setString(index++, combinadoStr);
				
				indexList++;
			}
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finalizar(ps, rs);
	}

	public boolean verificaCombinacao(List<Integer> combinados) {

		boolean res = true;
		 
		String combinadoStr= combinados.toString().replace("[", "");
		combinadoStr = combinadoStr.replace("]", "");
		combinadoStr = combinadoStr.replace(" ", "");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//Verifica se já existe a combinação no banco
		String query = "Select id from tb_teste where combinacao IN (?)";		
		try {
			
			this.conn = ConexaoBD.getInstance();
			
			ps = conn.prepareStatement(query);
			ps.setString(1, combinadoStr);
			
			rs = ps.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Insere ou não a nova combinação
		try {
			if(!rs.next()){
				
				query = "INSERT INTO tb_teste (combinacao) VALUES (?)";
				ps = conn.prepareStatement(query);
				ps.setString(1, combinadoStr);
				
				ps.executeUpdate();
				
				res =  false;
			} else {
				res =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finalizar(ps, rs);
		
		return res;
	}
	
	public void salvaSorteados(List<List<Integer>> jogo, int combinacoes, int corte) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//Monta ? da query
		String values = "(?),";
		for (int i = 1; i < corte; i++) {
			values += "(?),";
		}
		
		values = values.substring(0, values.length() -1);
		
		//Verifica se já existe a combinação no banco
		String query = "INSERT INTO tb_teste (combinacao) VALUES "+values;		
		try {
			
			this.conn = ConexaoBD.getInstance();
			
			ps = conn.prepareStatement(query);
			
			int index = 1;
			for (int i = 0; i < corte; i++) {
				
				String combinadoStr = listCombinados.get(indexList).toString().replace("[", "");
				combinadoStr = combinadoStr.replace("]", "");
				combinadoStr = combinadoStr.replace(" ", "");
				
				ps.setString(index++, combinadoStr);
				
				indexList++;
			}
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finalizar(ps, rs);
	}
	
	public static void inserirSorteados(List<String> linhaSorteada) {
		
		Connection connection;
		try {
			connection = ConexaoBD.getInstance();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		PreparedStatement ps = null;
		
		String query = "INSERT INTO public.tb_numeros_sorteados_15"
				+ "(id_concurso, data_sorteio, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, premio, ganhadores, rateio, estados) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";	
		try {

			connection = ConexaoBD.getInstance();

			ps = connection.prepareStatement(query);

			for (int i = 0; i < linhaSorteada.size(); i++) {
				
				String[] sep = linhaSorteada.get(i).split(":");
				
				// id = número do sorteio
				ps.setInt(1, Integer.parseInt(sep[0]));
				ps.setString(2, sep[1]);

				// Bolas
				ps.setInt(3, Integer.parseInt(sep[2]));
				ps.setInt(4, Integer.parseInt(sep[3]));
				ps.setInt(5, Integer.parseInt(sep[4]));
				ps.setInt(6, Integer.parseInt(sep[5]));
				ps.setInt(7, Integer.parseInt(sep[6]));
				ps.setInt(8, Integer.parseInt(sep[7]));
				ps.setInt(9, Integer.parseInt(sep[8]));
				ps.setInt(10, Integer.parseInt(sep[9]));
				ps.setInt(11, Integer.parseInt(sep[10]));
				ps.setInt(12, Integer.parseInt(sep[11]));
				ps.setInt(13, Integer.parseInt(sep[12]));
				ps.setInt(14, Integer.parseInt(sep[13]));
				ps.setInt(15, Integer.parseInt(sep[14]));
				ps.setInt(16, Integer.parseInt(sep[15]));
				ps.setInt(17, Integer.parseInt(sep[16]));

				// Infos
				ps.setString(18, sep[20]);
				ps.setInt(19, Integer.parseInt(sep[17]));
				ps.setString(20, sep[19]);

				String uf = sep[18];

				if (sep.length == 22) {
					uf += "-" + sep[21].replaceAll(" ", "");

					uf = uf.substring(0, uf.length() - 1);

				}
				ps.setString(21, uf);

				ps.executeUpdate();

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	private void finalizar(PreparedStatement ps, ResultSet rs) {
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

	public static int selectID() {
		Connection connection;
		try {
			connection = ConexaoBD.getInstance();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT id_concurso FROM tb_numeros_sorteados_15 ORDER BY id_concurso DESC LIMIT 1";
		try {

			connection = ConexaoBD.getInstance();

			ps = connection.prepareStatement(query);

			rs = ps.executeQuery();

			rs.next();
			
			return rs.getInt("id_concurso");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int verificaJogo() {
		Connection connection;
		try {
			connection = ConexaoBD.getInstance();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Integer> listJogado = new ArrayList<Integer>();
		listJogado.add(0);
		
		String query = "SELECT b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15 FROM tb_numeros_sorteados_15";
		try {

			connection = ConexaoBD.getInstance();

			ps = connection.prepareStatement(query);

			rs = ps.executeQuery();

			List<Integer> list = new ArrayList<Integer>();
			
			while (rs.next()) {
				
				list.add(rs.getInt("b1"));
				list.add(rs.getInt("b2"));
				list.add(rs.getInt("b3"));
				list.add(rs.getInt("b4"));
				list.add(rs.getInt("b5"));
				list.add(rs.getInt("b6"));
				list.add(rs.getInt("b7"));
				list.add(rs.getInt("b8"));
				list.add(rs.getInt("b9"));
				list.add(rs.getInt("b10"));
				list.add(rs.getInt("b11"));
				list.add(rs.getInt("b12"));
				list.add(rs.getInt("b13"));
				list.add(rs.getInt("b14"));
				list.add(rs.getInt("b15"));
				
				Collections.sort(list);
				
				if(list.equals(listJogado)) {
					
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
}
