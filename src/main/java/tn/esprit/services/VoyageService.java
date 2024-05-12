    package tn.esprit.services;

    import tn.esprit.entities.*;
    import tn.esprit.services.IService;
    import tn.esprit.utils.MyConnection;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;

    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;

    public class VoyageService implements IService<Voyage> {
        private Connection connection;

        public VoyageService(){
            connection= MyConnection.getInstance().getCnx();
        }
        @Override
        public int ajouter(Voyage voyage) throws SQLException {
            String sql = "INSERT INTO `voyage`(`vehicule_id`,`accomodation_id`,`evenement_id`,`depart`,`arrivee`,`utilisateur_id`) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, voyage.getVehicule_id());
            preparedStatement.setInt(2, voyage.getHebergement_id());
            preparedStatement.setInt(3, voyage.getEvenement_id());
            preparedStatement.setDate(4, voyage.getDepart());
            preparedStatement.setDate(5, voyage.getArrivee());
            preparedStatement.setInt(6, voyage.getUtilisateur_id());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int userId = -1; // Default value if no key is generated
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1); // Assuming the ID is in the first column
            }

            return userId;
        }

        @Override
        public void modifier(Voyage voyage) throws SQLException {
            String sql = "UPDATE `voyage` SET `vehicule_id`=?, `accomodation_id`=?, `evenement_id`=?, `depart`=?, `arrivee`=?, `utilisateur_id`=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, voyage.getVehicule_id());
                preparedStatement.setInt(2, voyage.getHebergement_id());
                preparedStatement.setInt(3, voyage.getEvenement_id());
                preparedStatement.setDate(4, voyage.getDepart());
                preparedStatement.setDate(5, voyage.getArrivee());
                preparedStatement.setInt(6, voyage.getUtilisateur_id());
                preparedStatement.setInt(7, voyage.getId());
                preparedStatement.executeUpdate();
            }
        }

        @Override
        public void supprimer(int id) throws SQLException {

        }

        @Override
        public List<Voyage> recuperer() throws SQLException {
            return null;
        }

        @Override
        public List<Voyage> recupererComPost(int id_post) throws SQLException {
            return null;
        }

        @Override
        public Post recupererPost(int id) throws SQLException {
            return null;
        }

        @Override
        public React recupererReact(int react_id) throws SQLException {
            return null;
        }

        @Override
        public void modifierReact(React react) throws SQLException {

        }

        @Override
        public List<Comment> recupererReply(int id_parent) {
            return null;
        }

        @Override
        public void supprimer(Voyage voyage) throws SQLException {
            String sql= "delete from voyage where id = ?";
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setInt(1,voyage.getId());
            preparedStatement.executeUpdate();
        }

        @Override
        public ObservableList<Voyage> afficher() throws SQLException {
            ObservableList<Voyage> voyages = FXCollections.observableArrayList();
            String sql = "SELECT * FROM voyage";
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    Voyage v = new Voyage();
                    v.setId(rs.getInt("id"));
                    v.setVehicule_id(rs.getInt("vehicule_id"));
                    v.setHebergement_id(rs.getInt("accomodation_id")); // Use correct column name
                    v.setEvenement_id(rs.getInt("evenement_id")); // Use correct column name
                    v.setDepart(rs.getDate("depart"));
                    v.setArrivee(rs.getDate("arrivee"));
                    v.setUtilisateur_id(rs.getInt("utilisateur_id"));
                    voyages.add(v);
                }
            }
            return voyages;
        }

        @Override
        public void supprimer(Groupe groupe) throws SQLException {

        }

        public List<String> getAllVoyages() throws SQLException {
            List<String> voyages = new ArrayList<>();
            String sql = "SELECT * FROM voyage";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    voyages.add(resultSet.getString("id"));
                }
            }
            return voyages;
        }
    }
