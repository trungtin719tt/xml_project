/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;
import jaxb.book.Book;
import jaxb.book.Review;
import utilities.DBUtils;

/**
 *
 * @author ASUS
 */
public class ReviewDAO implements Serializable{
    public void AddReview(Review review, int bookID) throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DBUtils.getConnection();
            String sql = "Insert into Review(ReviewBy, ReviewContent, Score, NumberOfLikes, BookID) values (?, ?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, review.getReviewBy());
            stmt.setString(2, review.getReviewContent());
            stmt.setFloat(3, review.getScore());
            stmt.setInt(4, review.getNumberOfLikes());
            stmt.setInt(5, bookID);
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
