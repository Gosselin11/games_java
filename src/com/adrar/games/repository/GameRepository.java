package com.adrar.games.repository;

import com.adrar.games.database.Request;
import com.adrar.games.model.Console;
import com.adrar.games.model.Game;

import java.sql.*;
import java.util.ArrayList;

public class GameRepository {
    //Attribut
    private final Connection connect;

    //Constructeur
    public GameRepository()
    {
        this.connect = new Request().getConnection();
    }

    public ArrayList<Game> findAll()
    {
        ArrayList<Game> games = new ArrayList<>();
        try {
            //Requête SQL
            String sql = "SELECT game.id, game.`type`, game.title, game.publish_at ," +
                    " game.id_console, console.name, console.manufacturer FROM game " +
                    "INNER JOIN console ON game.id_console = console.id";
            //Préparer la Requête
            PreparedStatement pstmt = this.connect.prepareStatement(sql);
            //Exécuter la requête
            ResultSet rs = pstmt.executeQuery();
            //Parcours du Resultat
            while (rs.next())
            {
                //Objet Console
                Console console = new Console();
                console.setName(rs.getString("name"));
                console.setManufacturer(rs.getString("manufacturer"));
                console.setId(rs.getInt("id_console"));
                //Objet Game
                Game game = new Game();
                game.setId(rs.getInt("id"));
                game.setTitle(rs.getString("title"));
                game.setType(rs.getString("type"));
                game.setPublishAt(rs.getDate("publish_at"));
                game.setConsole(console);
                games.add(game);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return games;
    }

    public Game find(int id)
    {
        Game game = null;
        try {
            //requête SQL
            String sql = "SELECT game.id, game.`type`, game.title, game.publish_at ," +
                    " game.id_console, console.name, console.manufacturer FROM game " +
                    "INNER JOIN console ON game.id_console = console.id WHERE game.id = ?";
            //préparation de la requête
            PreparedStatement pstmt = this.connect.prepareStatement(sql);
            //Assigner le paramètre
            pstmt.setInt(1, id);
            //exécuter la requête
            ResultSet rs = pstmt.executeQuery();
            //Traitement de la réponse
            while (rs.next()) {
                //Objet Console
                Console console = new Console();
                console.setName(rs.getString("name"));
                console.setManufacturer(rs.getString("manufacturer"));
                console.setId(rs.getInt("id_console"));
                //Objet Game
                game = new Game();
                game.setId(rs.getInt("id"));
                game.setTitle(rs.getString("title"));
                game.setType(rs.getString("type"));
                game.setPublishAt(rs.getDate("publish_at"));
                game.setConsole(console);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return game;
    }

    public Game saveGame(Game game)
    {
        try {
            //Requête SQL
            String sql = "INSERT INTO game(title, type, publish_at, id_console) VALUES (?, ?, ?, ?)";
            //préparer la requête
            PreparedStatement pstmt = this.connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            //Assigner les 4 paramètres
            pstmt.setString(1, game.getTitle());
            pstmt.setString(2, game.getType());
            pstmt.setDate(3, game.getPublishAt());
            pstmt.setInt(4, game.getConsole().getId());
            //Exécuter la requête
            int row = pstmt.executeUpdate();
            //Test si la requête est bien exécutée
            if (row > 0)
            {

                //Récupération de l'ID
                ResultSet rs = pstmt.getGeneratedKeys();
                //Boucle sur le resultat
                while (rs.next()) {
                    //Assigner l'ID
                    game.setId(rs.getInt(1));
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return game;
    }
}
