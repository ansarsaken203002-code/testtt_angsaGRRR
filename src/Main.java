import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/school_order";
        String user = "postgres";
        String password = "0000";

        Scanner sc = new Scanner(System.in);

        System.out.print("Login: ");
        String login = sc.nextLine();

        System.out.print("Password: ");
        String pass = sc.nextLine();

        try{

            Class.forName("org.postgresql.Driver");

            Connection con = DriverManager.getConnection(url , user , password);
            Statement st = con.createStatement();

            ResultSet auth = st.executeQuery(
                    "SELECT * FROM admin WHERE username = '" + login +
                            "' AND password = '" + pass + "'"
            );

            if(!auth.next()){
                System.out.println("Access denied");
                con.close();
                return;
            }

            System.out.println("Access granted");

            int choice = -1;

            while(choice != 0){

                System.out.println();
                System.out.println("1 - Make order");
                System.out.println("2 - Show orders");
                System.out.println("3 - Cancel last order");
                System.out.println("0 - Exit");

                choice = sc.nextInt();

                if(choice == 1){

                    double totalSum = 0;
                    String answer = "Y";

                    while(answer.equalsIgnoreCase("Y")){

                        ResultSet items = st.executeQuery(
                                "SELECT * FROM school_items ORDER BY id"
                        );

                        System.out.println("Items:");

                        while(items.next()){
                            System.out.println(
                                    items.getInt("id") + " " +
                                            items.getString("item_name") + " " +
                                            items.getDouble("price") + " " +
                                            items.getInt("quantity")
                            );
                        }

                        items.close();

                        System.out.print("Enter item id: ");
                        int id = sc.nextInt();

                        System.out.print("Enter quantity: ");
                        int q = sc.nextInt();

                        ResultSet one = st.executeQuery(
                                "SELECT * FROM school_items WHERE id = " + id
                        );

                        if(!one.next()){
                            System.out.println("Item not found");
                            break;
                        }

                        String name = one.getString("item_name");
                        double priceItem = one.getDouble("price");
                        int available = one.getInt("quantity");

                        if(q > available){
                            System.out.println("Not enough items");
                            break;
                        }

                        double price = priceItem * q;
                        totalSum = totalSum + price;

                        int newQ = available - q;

                        st.executeUpdate(
                                "UPDATE school_items SET quantity = " + newQ +
                                        " WHERE id = " + id
                        );

                        st.executeUpdate(
                                "INSERT INTO orders (item_name, quantity, total_price) VALUES (" +
                                        "'" + name + "', " +
                                        q + ", " +
                                        price + ")"
                        );

                        System.out.println("Added: " + name + " x" + q);
                        System.out.println("Price: " + price);

                        one.close();

                        System.out.print("Continue? (Y/N): ");
                        answer = sc.next();
                    }

                    System.out.println("Total sum: " + totalSum);
                }

                if(choice == 2){

                    ResultSet history = st.executeQuery(
                            "SELECT * FROM orders ORDER BY id"
                    );

                    System.out.println("Orders:");

                    while(history.next()){
                        System.out.println(
                                history.getInt("id") + " " +
                                        history.getString("item_name") + " " +
                                        history.getInt("quantity") + " " +
                                        history.getDouble("total_price")
                        );
                    }

                    history.close();
                }

                if(choice == 3){

                    ResultSet last = st.executeQuery(
                            "SELECT * FROM orders ORDER BY id DESC LIMIT 1"
                    );

                    if(!last.next()){
                        System.out.println("No orders to cancel");
                    }
                    else{

                        int oid = last.getInt("id");
                        String name = last.getString("item_name");
                        int q = last.getInt("quantity");

                        ResultSet item = st.executeQuery(
                                "SELECT * FROM school_items WHERE item_name = '" + name + "'"
                        );

                        if(item.next()){
                            int oldQ = item.getInt("quantity");
                            int newQ = oldQ + q;

                            st.executeUpdate(
                                    "UPDATE school_items SET quantity = " + newQ +
                                            " WHERE item_name = '" + name + "'"
                            );
                        }

                        st.executeUpdate(
                                "DELETE FROM orders WHERE id = " + oid
                        );

                        System.out.println("Last order canceled");
                    }

                }

            }

            st.close();
            con.close();

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
