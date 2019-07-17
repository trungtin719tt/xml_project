/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import jaxb.book.Book;
import jaxb.book.Review;
import utilities.DBUtils;
import utilities.StringUtils;

/**
 *
 * @author ASUS
 */
public class BookDAO implements Serializable {
    
    public boolean IsBookCrawled(String Hash) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            String sql = "Select 1 From Book where AllHash = ?";
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
    
    public int GetBookID(String name, String author) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            String sql = "Select ID From Book where Name = ? AND Author = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, author);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");

            }

            return -1;
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
    
    public void AddBook(Book book, String allHashCode) throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement stmt = null;
        if (GetBookID(book.getName(), book.getAuthor()) != -1){
            return;
        }
        try {
            con = DBUtils.getConnection();
            String sql = "Insert into Book(Name, Author, Score, AllHash, IsNew) values (?, ?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, book.getName());
            stmt.setString(2, book.getAuthor());
            stmt.setFloat(3, book.getScore());
            stmt.setString(4, allHashCode);
            stmt.setBoolean(5, true);
            stmt.executeUpdate();
            
            int bookID = GetBookID(book.getName(), book.getAuthor());
            ReviewDAO reviewDAO = new ReviewDAO();
            if (bookID != -1){
                for (Review review : book.getReview()) {
                    reviewDAO.AddReview(review, bookID);
                }
            }
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
