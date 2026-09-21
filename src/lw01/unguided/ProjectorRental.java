public class ProjectorRental extends Rental {

    public ProjectorRental(String id, int days) {
        super(id, days);
    }

    @Override
    public int calculateCharge() {
        int days = getDays();
        int charge;
        if (days <= 3) {
            charge = days * 60000;
        } else {
            charge = (3 * 60000) + ((days - 3) * 45000);
        }
        charge += 20000; // setup cost per complete copy
        return charge;
    }

    @Override
    public String label() {
        return "Projector";
    }
}