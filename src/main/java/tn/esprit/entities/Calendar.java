package tn.esprit.entities;

public class Calendar {

    private int id;
    private String Title;
    private String Start;
    private String End;
    private String Description;
    private int All_Day;
    private String Background_Color;
    private String Border_Color;
    private String Text_Color;
    private Moy_Transport id_transport;
    private int Passenger_Count;

    public Calendar() {
    }

    public Calendar(String Title, String Start, String End, String Description, int All_Day, String Background_Color, String Border_Color, String Text_Color, int Passenger_Count, Moy_Transport id_transport) {
        this.Title=Title;
        this.Start=Start;
        this.End=End;
        this.Description=Description;
        this.All_Day=All_Day;
        this.Background_Color=Background_Color;
        this.Border_Color=Border_Color;
        this.Text_Color=Text_Color;
        this.Passenger_Count=Passenger_Count;
        this.id_transport=id_transport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getAll_Day() {
        return All_Day;
    }

    public void setAll_Day(int all_Day) {
        All_Day = all_Day;
    }

    public String getBackground_Color() {
        return Background_Color;
    }

    public void setBackground_Color(String background_Color) {
        Background_Color = background_Color;
    }

    public String getBorder_Color() {
        return Border_Color;
    }

    public void setBorder_Color(String border_Color) {
        Border_Color = border_Color;
    }

    public String getText_Color() {
        return Text_Color;
    }

    public void setText_Color(String text_Color) {
        Text_Color = text_Color;
    }

    public Moy_Transport getId_transport() {
        return id_transport;
    }

    public void setId_transport(Moy_Transport id_transport) {
        this.id_transport = id_transport;
    }

    public int getPassenger_Count() {
        return Passenger_Count;
    }

    public void setPassenger_Count(int passenger_Count) {
        Passenger_Count = passenger_Count;
    }

   /* @Override
    public String toString() {
        return this.id_transport.getTransport_Model();
    }*/
}
