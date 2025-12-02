package com.adrar.games.repository;

import com.adrar.games.database.Request;
import com.adrar.games.model.Console;

import java.sql.*;
import java.util.ArrayList;

public class ConsoleRepository {
    //Attribut
    private Connection connect;

    //Constructeur
    public ConsoleRepository()
    {
        Request request = new Request();
        this.connect = request.getConnection();
    }

    //Méthode pour ajouter une console (Console) en BDD
    public Console saveConsole(Console console)
    {
        try {
            //requête SQL
            String sql = "INSERT INTO console (name, manufacturer) VALUES (?, ?)";
            //Préparation de la requête
            PreparedStatement pstmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Assigner les paramètres
            pstmt.setString(1, console.getName());
            pstmt.setString(2, console.getManufacturer());
            //Exécuter la requête
            int addRows = pstmt.executeUpdate();

            //Test si la requête à fonctionné
            if (addRows > 0) {
                //récupération de l'ID
                ResultSet rs = pstmt.getGeneratedKeys();
                //Test si on à une réponse
                if (rs.next()) {
                    //ide de l'enregistrement
                    int id  = rs.getInt(1);
                    //set de l' id
                    console.setId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return console;
    }

    //Méthode qui retourne une ArrayList de console (Console)
    public ArrayList<Console> findAll()
    {
        ArrayList<Console> consoles = new ArrayList<>();
        try {
            //Ecrire la requête
            String sql = "SELECT c.id, c.name, c.manufacturer FROM console AS c";
            //Préparer la requête
            PreparedStatement pstmt = connect.prepareStatement(sql);
            //Exécution de la requête
            ResultSet rs = pstmt.executeQuery();
            //Boucle sur le résultat
            while (rs.next()) {
                //Créer un objet console (Console)
                Console console = new Console();
                //Setter les valeurs
                console.setId(rs.getInt("id"));
                console.setName(rs.getString("name"));
                console.setManufacturer(rs.getString("manufacturer"));
                //Ajouter la console (Console) à la liste
                consoles.add(console);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return consoles;
    }

    //Méthode qui retourne une console depuis son id
    public Console find(int id)
    {
        //Création d'un objet console à null
        Console console = null;
        try {
            //Ecrire la requête
            String sql = "SELECT c.id, c.name, c.manufacturer FROM console AS c WHERE c.id = ?";
            //Préparer la requête
            PreparedStatement pstmt = connect.prepareStatement(sql);
            //Assigner le paramètre
            pstmt.setInt(1, id);
            //Exécution de la requête
            ResultSet rs = pstmt.executeQuery();
            //Boucle sur le résultat
            while (rs.next()) {
                //Créer un objet console (Console)
                console = new Console();
                //Setter les valeurs
                console.setId(rs.getInt("id"));
                console.setName(rs.getString("name"));
                console.setManufacturer(rs.getString("manufacturer"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return console;
    }

    //méthode qui test si une console (Console) avec son name existe
    public boolean isExistsByName(Console console)
    {
        try {
            //Requête SQL
            String sql = "SELECT c.id FROM console AS c WHERE c.`name` = ?";
            //Préparer la requête
            PreparedStatement pstmt = connect.prepareStatement(sql);
            //Assigner le paramètre
            pstmt.setString(1, console.getName());
            //Exécution de la requête
            ResultSet rs = pstmt.executeQuery();
            //parcours du resultat
            if (rs.next()) {
                //test si l'id existe
                if (rs.getInt("id") > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
