/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import utilities.DBUtils;

/**
 *
 * @author ASUS
 */
public class PageDAO implements Serializable{
    public boolean IsPageCrawled(String Hash) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            String sql = "Select 1 From Page where PageContentHash = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, Hash);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return true;

            }

            return false;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public void addNewPage(String pageLink, String pageContentHash) throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DBUtils.getConnection();
            String sql = "Insert into Page(PageLink, PageContentHash) values (?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, pageLink);
            stmt.setString(2, pageContentHash);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
