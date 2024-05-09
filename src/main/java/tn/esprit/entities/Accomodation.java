package tn.esprit.entities;

public class Accomodation {

        private String idaccomodation;
        private String lieuaccomodation;
        private String descriptionaccomodation;

        
        public Accomodation(String idaccomodation, String lieuaccomodation , String descriptionaccomodation) {
            this.idaccomodation = idaccomodation;
            this.lieuaccomodation = lieuaccomodation;
            this.descriptionaccomodation = descriptionaccomodation;
        }

        public Accomodation(String lieuaccomodation, String descriptionaccomodation) {
            this.lieuaccomodation = lieuaccomodation;
            this.descriptionaccomodation=descriptionaccomodation;

        }

        public Accomodation(){}

        public String getidaccomodation() {
            return idaccomodation;
        }

        public String getlieuaccomodation () {
            return lieuaccomodation;
        }

        public String getdescriptionaccomodation () {
            return descriptionaccomodation;
        }


        public void setidaccomodation(String idaccomodation) {
            this.idaccomodation = idaccomodation;
        }

        public void setlieuaccomodation(String lieuaccomodation) {
            this.lieuaccomodation = lieuaccomodation;
        }

        public void setdescriptionaccomodation(String descriptionaccomodation) {
            this.descriptionaccomodation = descriptionaccomodation;
        }


        @Override
        public String toString() {
            return "\n accomodation\n{  " +
                    "id=" + idaccomodation +
                    ", lieu='" + lieuaccomodation + '\'' +
                    ", description='" + descriptionaccomodation + '\'' +
                    "  }\n";
        }


    }


