package sample;

public class InHouse extends Part{
    int machineid;

    public InHouse(String name, double price, int stock, int min, int max, int machineid) {
        super(name, price, stock, min, max);
        this.machineid = machineid;
    }

    public void setMachineid(int machineid){
        this.machineid = machineid;
    }
    public int getMachineid(){
        return this.machineid;
    }
}
