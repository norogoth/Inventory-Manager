package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static ObservableList<Part> listOfParts = FXCollections.observableArrayList();
    private static int nextID = 100;

    public static int getNextID(){
        return nextID;
    }
    public static void setNextID(int id){
        nextID = id;
    }

    /*public Inventory() {
        this.allParts = FXCollections.observableArrayList();
        this.allProducts = FXCollections.observableArrayList();
    }*/
    public static void generateSampleData(){

        Part partGear = new InHouse("sample inHouse",1.50,3,1,100, 55);
        Part partPivot = new OutSourced("sample outSourced",5.45,0,1,3, "Umbrella");
        allParts.add(partGear);
        allParts.add(partPivot);
        Product product = new Product(listOfParts, 131,"piston",1.50,3,2,10);
        allProducts.add(product);
    }
    public static void addPart(Part newPart){ allParts.add(newPart); }
    public static void addProduct (Product newProduct){
        allProducts.add(newProduct);
    }
    public Product lookupProduct(int productid) {
        Product nullProduct = null;
        try {
            for (int i = 0; i < allProducts.size(); i++) {
                if (this.allProducts.get(i).getId() == productid) {
                    return this.allProducts.get(i);
                }
            }
        }
        catch (Exception e){
            System.out.println("error");
        }
        return nullProduct;
    }

    public static Part lookupPart(int partid){
        Part nullPart = null;
        try {
            for (int i = 0; i < allParts.size(); i++){
                if (allParts.get(i).getId() == partid) {
                    return allParts.get(i);
                }
            }
        }
        catch (Exception e){
            System.out.println("error");
        }
        return nullPart;
    }

    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        //Part nullPart = null;
        for (int i = 0; i < allParts.size(); i++){
            if (allParts.get(i).getName().toLowerCase().startsWith(partName.toLowerCase())){
                matchingParts.add(allParts.get(i));
            }
        }
        return matchingParts;
    }

    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        //Product nullProducts = null;
        for (int i = 0; i < allProducts.size(); i++){
            if (allProducts.get(i).getName().toLowerCase().startsWith(productName.toLowerCase())){
                matchingProducts.add(allProducts.get(i));
            }
        }
        return matchingProducts;
    }

    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index,newProduct);
    }

    public static boolean deletePart(Part selectPart){
        boolean wasItThere = allParts.remove(selectPart);
        Inventory.allParts.remove(selectPart);
        return wasItThere;
    }

    public static boolean deleteProduct(Product selectProduct){
        boolean wasItThere = allProducts.remove(selectProduct);
        Inventory.allProducts.remove(selectProduct);
        return wasItThere;
    }
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    public static ObservableList<Product> getAllProduct(){
        return allProducts;
    }
}
