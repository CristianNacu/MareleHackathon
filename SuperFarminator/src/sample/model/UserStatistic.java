package sample.model;

public class UserStatistic {
    private int idUser;
    private int nrTaskuri;

    public UserStatistic(int idUser, int nrTaskuri) {
        this.idUser = idUser;
        this.nrTaskuri = nrTaskuri;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getNrTaskuri() {
        return nrTaskuri;
    }

    public void setNrTaskuri(int nrTaskuri) {
        this.nrTaskuri = nrTaskuri;
    }
}
