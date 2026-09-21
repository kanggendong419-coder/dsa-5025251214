public class LaptopRental extends Rental {

    public LaptopRental(String id, int days) {
        super(id, days);
    }

    @Override
    public int calculateCharge() {
        int charge = getDays() * 40000;
        charge += 10000;
        return charge;
    }

    @Override
    public String label() {
        return "Laptop";
    }
} 
    

