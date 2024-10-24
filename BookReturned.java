/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LiabraryProjectManagement;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sun.applet.Main;

/**
 *
 * @author Nikhil
 */
public class BookReturned extends javax.swing.JFrame {

    DefaultListModel mod;
    String searchSuggetion = "";
    /**
     * Creates new form BookReturned
     */
    DefaultTableModel mod1;

    public BookReturned() {
        initComponents();
        notifyOverdueBooks();
        menu.add(panel);
        mod = new DefaultListModel();
        list.setModel(mod);

        returnBookAndUpdateStock();
    }

    public void notifyOverdueBooks() {
        try {
            // Get current date
            Date currentDate = new Date();

            // Query for overdue books
            for (int i = 0; i < table2.getRowCount(); i++) {
                // Get due date from table
                String dueDateString = (String) table2.getValueAt(i, 5);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date dueDate = dateFormat.parse(dueDateString);
                if (currentDate.after(dueDate)) {
                    String studentID = (String) table2.getValueAt(i, 0);
                    String bookName = (String) table2.getValueAt(i, 1);
                    String bookID = (String) table2.getValueAt(i, 2);
                    String studentName = (String) table2.getValueAt(i, 3);
                    String message = "Dear " + studentName + ",\n\n";
                    message += "This is to inform you that the book'"+bookName+"'(ID:"+bookID+"),("+studentID+")";
                    message += "issued to you has not been returned on time. Please return it as soon as possible ";
                    message += "to avoid any late charges.\n\n";
                    message += "Thank you,\nLibrary Management System";

                    
                    Date returnDate = returndatebtn.getDate();
        //Date dueDate = new SimpleDateFormat("dd/MM/yyyy").parse(duedtx.getText());
        
        
        long diffInMillies = Math.abs(returnDate.getTime() - dueDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        
        
        if (returnDate.after(dueDate)) {
            double chargePerDay = 10.0; 
            double charges = diff * chargePerDay;
            String message1 = String.format("You returned the book after the due date. Charges: %.2f", charges);
            JOptionPane.showMessageDialog(this, message1, "Charges", JOptionPane.WARNING_MESSAGE);
        }
                    JOptionPane.showMessageDialog(null, message, "Overdue Book Notification", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
        
    }
    public void Clear() {
        bid.setText(" ");
        bname.setText(" ");
        sid.setText(" ");
        sname.setText(" ");
        issuetx.setText(" ");
        duedtx.setText(" ");

        JOptionPane.showMessageDialog(null, "Form Cleared");
    }

  public void returnBookAndUpdateStock() {
            Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            Statement st = con.createStatement();
            rs = st.executeQuery("select * from register1");

            DefaultTableModel mod1 = (DefaultTableModel) table2.getModel();
            mod1.setRowCount(0); 

            while (rs.next()) {
                String ids = rs.getString("sid");
                String bookname = rs.getString("bname");
                
                String studentname = rs.getString("name");
                String issue = rs.getString("issuedate");
                String due = rs.getString("duedate");
                String return1=rs.getString("returndate");
                Object[] ob = {ids, bookname, studentname, issue, due,return1};
                mod1.addRow(ob);
            }

            String stockQuery = "select * from register1 WHERE bname = ?";
            PreparedStatement stockPst = con.prepareStatement(stockQuery);
            stockPst.setString(1, bname.getText());
            ResultSet stockRs = stockPst.executeQuery();

            if (stockRs.next()) {
                Date dob = returndatebtn.getDate();
                if (dob == null) {
                    //JOptionPane.showMessageDialog(null, "Please select a return date.");
                    return;
                }

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String returndate1 = formatter.format(dob);

                String returnQuery = "update register1(bname, sid, name, issuedate, duedate, returndate) VALUES ('"+returndate1+"')";
                PreparedStatement returnPst = con.prepareStatement(returnQuery);
               
                int i = returnPst.executeUpdate();
                
                    
                    
                    if (i > 0) {
                        JOptionPane.showMessageDialog(null, "Book Returned Successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to Return");
                    }
                 
            } else {
               
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        } 
  

  }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new javax.swing.JPopupMenu();
        jScrollPane2 = new javax.swing.JScrollPane();
        list = new javax.swing.JList();
        panel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        duedtx = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        sid = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        sname = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        issuetx = new javax.swing.JTextField();
        searchbtn = new javax.swing.JButton();
        returnbtn = new javax.swing.JButton();
        printinvoice = new javax.swing.JButton();
        deletebtn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        returndatebtn = new com.toedter.calendar.JDateChooser();
        bname = new javax.swing.JTextField();
        clearbtn1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        table2 = new javax.swing.JTable();
        bid = new javax.swing.JTextField();
        closebtn1 = new javax.swing.JButton();

        menu.setFocusable(false);

        list.setFocusable(false);
        jScrollPane2.setViewportView(list);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 280, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1300, 800));

        jPanel1.setBackground(new java.awt.Color(0, 255, 255));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Book Return");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(340, 0, 180, 30);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Return Date");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(200, 480, 150, 40);
        jPanel1.add(duedtx);
        duedtx.setBounds(370, 420, 280, 40);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Book Id");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(200, 210, 160, 30);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Book Name");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(200, 130, 150, 40);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Student Id");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(200, 70, 160, 40);
        jPanel1.add(sid);
        sid.setBounds(370, 70, 280, 40);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Student Name");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(200, 280, 140, 40);
        jPanel1.add(sname);
        sname.setBounds(370, 280, 280, 40);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Issue Date");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(200, 350, 160, 40);
        jPanel1.add(issuetx);
        issuetx.setBounds(370, 350, 280, 40);

        searchbtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        searchbtn.setText("Search");
        searchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbtnActionPerformed(evt);
            }
        });
        jPanel1.add(searchbtn);
        searchbtn.setBounds(690, 70, 100, 40);

        returnbtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        returnbtn.setText("Return");
        returnbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnbtnActionPerformed(evt);
            }
        });
        jPanel1.add(returnbtn);
        returnbtn.setBounds(160, 540, 100, 40);

        printinvoice.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        printinvoice.setText("Print Invoice");
        printinvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printinvoiceActionPerformed(evt);
            }
        });
        jPanel1.add(printinvoice);
        printinvoice.setBounds(660, 540, 130, 40);

        deletebtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        deletebtn.setText("Delete");
        deletebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebtnActionPerformed(evt);
            }
        });
        jPanel1.add(deletebtn);
        deletebtn.setBounds(400, 540, 100, 40);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Due Date");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(200, 420, 130, 40);

        returndatebtn.setDateFormatString("dd/MM/yyyy");
        jPanel1.add(returndatebtn);
        returndatebtn.setBounds(370, 482, 280, 40);

        bname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnameActionPerformed(evt);
            }
        });
        bname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                bnameKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                bnameKeyTyped(evt);
            }
        });
        jPanel1.add(bname);
        bname.setBounds(370, 130, 280, 40);

        clearbtn1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        clearbtn1.setText("Clear");
        clearbtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbtn1ActionPerformed(evt);
            }
        });
        jPanel1.add(clearbtn1);
        clearbtn1.setBounds(520, 540, 100, 40);

        table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student Id", "Book Name", "Student Name", "Issue Date", "Due Date", "Return Date"
            }
        ));
        jScrollPane3.setViewportView(table2);

        jPanel1.add(jScrollPane3);
        jScrollPane3.setBounds(270, 580, 452, 140);
        jPanel1.add(bid);
        bid.setBounds(370, 200, 280, 40);

        closebtn1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        closebtn1.setText("Close");
        closebtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closebtn1ActionPerformed(evt);
            }
        });
        jPanel1.add(closebtn1);
        closebtn1.setBounds(280, 540, 100, 40);

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 4196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1007, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void printinvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printinvoiceActionPerformed
        // TODO add your handling code here:
      /*  Document doc=new Document();
         try{
         String id=bid.getText();
        
         String n=bname.getText();
        
         String ids=sid.getText();
        
         String s=sname.getText();
        
         SimpleDateFormat myformate=new SimpleDateFormat();
         Calendar calendar=Calendar.getInstance();
         PdfWriter.getInstance(doc, new FileOutputStream(InventoryUtls.billPath+ids+".pdf"));
         Paragraph project=new Paragraph("Inventory ");
         doc.add(project);
            
            
         Paragraph line=new Paragraph("-");
         doc.add(line);
            
            
         Paragraph detail=new Paragraph("\tbid"+id+"bname"+n+"sid"+ids+"sname"+s+"\nDate :"+myformate.format(calendar.getTime()));
         doc.add(detail);
         doc.add(line);
            
         PdfPTable t=new PdfPTable(4);
         PdfPCell bookid= new PdfPCell(new Phrase("bid"));
         PdfPCell bookname= new PdfPCell(new Phrase("bname"));
         PdfPCell Studentid= new PdfPCell(new Phrase("studentid"));
         PdfPCell studentname= new PdfPCell(new Phrase("sname"));
            
         t.addCell(bookid);
         t.addCell(bookname);
         t.addCell(Studentid);
         t.addCell(studentname);
            
            
         doc.add(t);
         doc.add(line);
         setVisible(false);
            
         Reciept2 r=new Reciept2();
         r.setVisible(true);
         } catch(Exception ex){System.out.println(""+ex);}
         int choice=JOptionPane.showConfirmDialog(null, "Do you want Print Invoice");
         if(choice!=JOptionPane.YES_OPTION) {
           
         } else {
           
         }*/


    }//GEN-LAST:event_printinvoiceActionPerformed

    private void deletebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtnActionPerformed
        // TODO add your handling code here:
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            String sql = "Delete from register1 where sid='" + sid.getText() + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            int i = pst.executeUpdate();
            if (i > 0) {
                JOptionPane.showMessageDialog(null, "Deleted");
            } else {
                JOptionPane.showMessageDialog(null, "Table Not Deleted");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Table Not Selected");
            System.out.println("" + ex);
        }
    }//GEN-LAST:event_deletebtnActionPerformed

    private void returnbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnbtnActionPerformed
        // TODO add your handling code here:

        try {
            Date dob = returndatebtn.getDate();
            SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
            String returndate1 = formater.format(dob);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            String sql = "update register1 set returndate = '"+returndate1+"', totalbooks = totalbooks + 1 where sid = '"+sid.getText()+"'";            
            PreparedStatement pst = con.prepareStatement(sql);
            int i = pst.executeUpdate();    
            
            String updateSql = "update books set quantity = quantity + 1 where name ='"+bname.getText()+"'";
        PreparedStatement updatePst = con.prepareStatement(updateSql);
        updatePst.executeUpdate();
               
         JOptionPane.showMessageDialog(null, "Book Returned");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Student not available or Book not Available");
            System.out.println(" Book not Available " + ex);
        }

    }//GEN-LAST:event_returnbtnActionPerformed

    private void searchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbtnActionPerformed
        // TODO add your handling code here:
        try {
            int search = 0;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            String sql = "select * from register1 ";
            

            PreparedStatement pst = con.prepareStatement(sql);
            
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                search = 1;
                
                String name = rs.getString("bname");
                bname.setText(String.valueOf(name));
                String s = rs.getString("name");
                sname.setText(String.valueOf(s));

                String issuedate = rs.getString("issuedate");
                Date issue = new SimpleDateFormat("dd/MM/yyyy").parse(issuedate);
                issuetx.setText(new SimpleDateFormat("dd/MM/yyyy").format(issue));

                String d = rs.getString("duedate");
                Date duedate = new SimpleDateFormat("dd/MM/yyyy").parse(d);
                duedtx.setText(new SimpleDateFormat("dd/MM/yyyy").format(duedate));

            } else {
                JOptionPane.showMessageDialog(null, "Incorrect BookId and Incorrect StudentId");
            }

        } catch (Exception ex) {
            System.out.println("Connection error" + ex);
        }
    }//GEN-LAST:event_searchbtnActionPerformed

    private void bnameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bnameKeyTyped
        // TODO add your handling code here:
       /* try{
         String name=bname.getText();
         String names=sname.getText();
        
         Class.forName("com.mysql.jdbc.Driver");
         Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root" ,"");
         String sql = "SELECT nb.*, ib.* FROM new_book nb JOIN issued_book ib on nb.bname = ib.sname where (ib.bname)  LIKE '%"+name+"%' AND (ib.sname) LIKE '%"+names+"%'";       //"Select * from table_emp where (ename) like '%"+name+"%'";
         PreparedStatement pst=con.prepareStatement(sql);
         ResultSet rs=pst.executeQuery();
         if(rs.next()){
         String n=rs.getString("bname");
         bname.setText(String.valueOf(n));
         String name1=rs.getString("sname");
         sname.setText(String.valueOf(name1));
         }else{
        
         }
        
         }catch(Exception ex){System.out.println(""+ex);}*/
    }//GEN-LAST:event_bnameKeyTyped

    private void bnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bnameKeyReleased
        // TODO add your handling code here:
        String search = bname.getText();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            String sql = "select * from register1 where (bname) like '%" + search + "%'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            search = search.replace("", "");
            if (search.equals("")) {
                mod.removeAllElements();
                for (Object Item : searchSuggetion(search)) {
                    mod.addElement(Item.toString());
                }

                bname.setText("");

            } else if (rs.next()) {
                String n = rs.getString("bname").trim();
                bname.setText(n);
                menu.show(bname, 0, bname.getHeight());
            } else {

            }
        } catch (Exception ex) {
            System.out.println("" + ex);
        }
    }//GEN-LAST:event_bnameKeyReleased

    private void bnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bnameActionPerformed

    private void clearbtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtn1ActionPerformed
        // TODO add your handling code here:
        Clear();
    }//GEN-LAST:event_clearbtn1ActionPerformed

    private void closebtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebtn1ActionPerformed
        // TODO add your handling code here:
        Menu menu = new Menu();
        menu.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_closebtn1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BookReturned.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookReturned.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookReturned.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookReturned.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookReturned().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bid;
    private javax.swing.JTextField bname;
    private javax.swing.JButton clearbtn1;
    private javax.swing.JButton closebtn1;
    private javax.swing.JButton deletebtn;
    private javax.swing.JTextField duedtx;
    private javax.swing.JTextField issuetx;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList list;
    private javax.swing.JPopupMenu menu;
    private javax.swing.JPanel panel;
    private javax.swing.JButton printinvoice;
    private javax.swing.JButton returnbtn;
    private com.toedter.calendar.JDateChooser returndatebtn;
    private javax.swing.JButton searchbtn;
    private javax.swing.JTextField sid;
    private javax.swing.JTextField sname;
    private javax.swing.JTable table2;
    // End of variables declaration//GEN-END:variables

    private Iterable searchSuggetion(String search) {
        List suggestions = new ArrayList();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            String sql = "select * from new_book where (bname) LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + search + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String bookName = rs.getString("bname");
                suggestions.add(bookName);
            }

            con.close();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);

        }

        return suggestions;
    }

}
