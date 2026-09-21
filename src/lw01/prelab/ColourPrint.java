public class ColourPrint extends PrintJob {

    public ColourPrint(String id, int pages) {
        super(id, pages);
    }

    @Override
    public int calculateCharge() {
        int pages = getPages();
        int charge;
        if (pages <= 10) {
            charge = pages * 1500;
        } else {
            charge = (10 * 1500) + ((pages - 10) * 45000);
        }
        charge += 2000; // setup cost per complete copy
        return charge;
    }

    @Override
    public String label() {
        return "Colour";
    }
}