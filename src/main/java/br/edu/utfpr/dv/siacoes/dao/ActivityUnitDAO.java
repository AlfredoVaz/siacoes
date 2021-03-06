package br.edu.utfpr.dv.siacoes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;

import br.edu.utfpr.dv.siacoes.log.UpdateEvent;
import br.edu.utfpr.dv.siacoes.model.ActivityUnit;

public class ActivityUnitDAO extends AbstractDAO<ActivityUnit> {
	
	@Override
	public List<ActivityUnit> listAll() throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try(Connection conn = ConnectionDAO.getInstance().getConnection()){
			stmt = conn.prepareStatement("SELECT * FROM activityunit ORDER BY description");
			List<ActivityUnit> list = new ArrayList<ActivityUnit>();
			while(rs.next()){
				list.add(this.loadObject(rs));
			}
			
			return list;
		}finally{
			ConnectionDAO.closeStatement(stmt, rs);
		}
	}
	
	public ActivityUnit findById(int id) throws SQLException{
		
		try(Connection conn = ConnectionDAO.getInstance().getConnection(); 
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM activityunit WHERE idActivityUnit=?")){
			
			stmt.setInt(1, id);
			try(ResultSet rs = stmt.executeQuery()) {
				if(rs.next()){
					return this.loadObject(rs);
				}else{
					return null;
				}
			} catch (SQLException e) {
				throw new SQLException(e);
			}
			
		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}
	
	public int save(int idUser, ActivityUnit unit) throws SQLException{
		boolean insert = (unit.getIdActivityUnit() == 0);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "";
		
		try(Connection conn = ConnectionDAO.getInstance().getConnection()){

			
			if(insert){
				stmt = conn.prepareStatement("INSERT INTO activityunit(description, fillAmount, amountDescription) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			}else{
				stmt = conn.prepareStatement("UPDATE activityunit SET description=?, fillAmount=?, amountDescription=? WHERE idActivityUnit=?");
			}
			
			stmt.setString(1, unit.getDescription());
			stmt.setInt(2, (unit.isFillAmount() ? 1 : 0));
			stmt.setString(3, unit.getAmountDescription());
			
			if(!insert){
				stmt.setInt(4, unit.getIdActivityUnit());
			}
			
			stmt.execute();
			
			if(insert){
				rs = stmt.getGeneratedKeys();
				
				if(rs.next()){
					unit.setIdActivityUnit(rs.getInt(1));
				}
				
				new UpdateEvent(conn).registerInsert(idUser, unit);
			} else {
				new UpdateEvent(conn).registerUpdate(idUser, unit);
			}
			
			return unit.getIdActivityUnit();
		}finally{
			ConnectionDAO.closeStatement(stmt, rs);
		}
	}
	
	public ActivityUnit loadObject(ResultSet rs) throws SQLException{
		ActivityUnit unit = new ActivityUnit();
		
		unit.setIdActivityUnit(rs.getInt("idActivityUnit"));
		unit.setDescription(rs.getString("Description"));
		unit.setFillAmount(rs.getInt("fillAmount") == 1);
		unit.setAmountDescription(rs.getString("amountDescription"));
		
		return unit;
	}

	@Override
	public List<ActivityUnit> listAll(boolean b) throws SQLException {
		throw new NotImplementedException("method not overridden");
	}
}
