package package3;

class Saving {
    @Save
    private int i;
    private int notSaving;
    @Save
    private Integer j;
    @Save
    private String s;
    @Save
    private boolean l;
    
    Saving(int i, int notSaving, Integer j, String s, boolean l) {
        this.i = i;
        this.notSaving = notSaving;
        this.j = j;
        this.s = s;
        this.l = l;
    }
    
    private Saving() {

    }


}