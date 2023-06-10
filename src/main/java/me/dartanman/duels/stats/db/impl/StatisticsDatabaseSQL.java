package me.dartanman.duels.stats.db.impl;

import me.dartanman.duels.Duels;
import me.dartanman.duels.stats.StatisticsManager;
import me.dartanman.duels.stats.db.StatisticsDatabase;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class StatisticsDatabaseSQL implements StatisticsDatabase
{

    // UUID (PK), Last_Known_Name, Wins, Losses, Kills, Deaths

    private final StatisticsManager manager;
    private final Duels plugin;

    public StatisticsDatabaseSQL(Duels plugin, StatisticsManager manager)
    {
        this.plugin = plugin;
        this.manager = manager;

        setup();
    }

    private void setup()
    {
        Connection connection = getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement(Constants.CREATE_TABLE_STMT);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe("Duels experienced an error while attempting to create and/or locate a table in the SQL Database!");
            Bukkit.getLogger().severe(e.toString());
        }
    }

    private Connection connection;

    private Connection getConnection()
    {
        String address = plugin.getConfig().getString("Statistics.SQL.Address");
        String port = plugin.getConfig().getString("Statistics.SQL.Port");
        String db = plugin.getConfig().getString("Statistics.SQL.Database");
        String user = plugin.getConfig().getString("Statistics.SQL.Username");
        String pass = plugin.getConfig().getString("Statistics.SQL.Password");

        final String DB_URL = "jdbc:mysql://" + address + ":" + port + "/" + db + "?characterEncoding=utf8";
        try
        {
            if(connection == null || connection.isClosed())
            {
                connection = DriverManager.getConnection(DB_URL, user, pass);
            }
        } catch (SQLException e)
        {
            Bukkit.getLogger().severe("Duels experienced an error while attempting to connect to the SQL Database!");
            Bukkit.getLogger().severe(e.toString());
        }
        return connection;
    }

    @Override
    public int getWins(UUID uuid)
    {
        Connection connection = getConnection();
        int wins = 0;
        try
        {
            PreparedStatement stmt = connection.prepareStatement(Constants.GET_WINS_STMT);
            stmt.setString(1, uuid.toString());
            ResultSet result = stmt.executeQuery();
            if(result.next())
            {
                wins = result.getInt("Wins");
            }
            result.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe("Duels experienced an error while attempting to retrieve a win-count from the SQL Database!");
            Bukkit.getLogger().severe(e.toString());
        }
        return wins;
    }

    @Override
    public int getLosses(UUID uuid)
    {
        Connection connection = getConnection();
        int losses = 0;
        try
        {
            PreparedStatement stmt = connection.prepareStatement(Constants.GET_LOSSES_STMT);
            stmt.setString(1, uuid.toString());
            ResultSet result = stmt.executeQuery();
            if(result.next())
            {
                losses = result.getInt("Losses");
            }
            result.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe("Duels experienced an error while attempting to retrieve a loss-count from the SQL Database!");
            Bukkit.getLogger().severe(e.toString());
        }
        return losses;
    }

    @Override
    public int getKills(UUID uuid)
    {
        Connection connection = getConnection();
        int kills = 0;
        try
        {
            PreparedStatement stmt = connection.prepareStatement(Constants.GET_KILLS_STMT);
            stmt.setString(1, uuid.toString());
            ResultSet result = stmt.executeQuery();
            if(result.next())
            {
                kills = result.getInt("Kills");
            }
            result.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe("Duels experienced an error while attempting to retrieve a kill-count from the SQL Database!");
            Bukkit.getLogger().severe(e.toString());
        }
        return kills;
    }

    @Override
    public int getDeaths(UUID uuid)
    {
        Connection connection = getConnection();
        int deaths = 0;
        try
        {
            PreparedStatement stmt = connection.prepareStatement(Constants.GET_DEATHS_STMT);
            stmt.setString(1, uuid.toString());
            ResultSet result = stmt.executeQuery();
            if(result.next())
            {
                deaths = result.getInt("Deaths");
            }
            result.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe("Duels experienced an error while attempting to retrieve a death-count from the SQL Database!");
            Bukkit.getLogger().severe(e.toString());
        }
        return deaths;
    }

    @Override
    public void setWins(UUID uuid, int wins)
    {
        Connection connection = getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement(Constants.SET_WINS_STMT);
            stmt.setInt(1, wins);
            stmt.setString(2, uuid.toString());
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe("Duels experienced an error while attempting to set a win-count in the SQL Database!");
            Bukkit.getLogger().severe(e.toString());
        }
    }

    @Override
    public void setLosses(UUID uuid, int losses)
    {
        Connection connection = getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement(Constants.SET_LOSSES_STMT);
            stmt.setInt(1, losses);
            stmt.setString(2, uuid.toString());
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe("Duels experienced an error while attempting to set a loss-count in the SQL Database!");
            Bukkit.getLogger().severe(e.toString());
        }
    }

    @Override
    public void setKills(UUID uuid, int kills)
    {
        Connection connection = getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement(Constants.SET_KILLS_STMT);
            stmt.setInt(1, kills);
            stmt.setString(2, uuid.toString());
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe("Duels experienced an error while attempting to set a kill-count in the SQL Database!");
            Bukkit.getLogger().severe(e.toString());
        }
    }

    @Override
    public void setDeaths(UUID uuid, int deaths)
    {
        Connection connection = getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement(Constants.SET_DEATHS_STMT);
            stmt.setInt(1, deaths);
            stmt.setString(2, uuid.toString());
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe("Duels experienced an error while attempting to set a death-count in the SQL Database!");
            Bukkit.getLogger().severe(e.toString());
        }
    }

    @Override
    public void registerNewPlayer(UUID uuid, String name)
    {
        Connection connection = getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement(Constants.REGISTER_PLAYER_STMT);
            stmt.setString(1, uuid.toString());
            stmt.setString(2, name);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe("Duels experienced an error while attempting to register a player in the SQL Database!");
            Bukkit.getLogger().severe(e.toString());
        }
    }

    @Override
    public boolean isRegistered(UUID uuid)
    {
        String name = getLastKnownName(uuid);
        return !name.equals("[Unknown]");
    }

    @Override
    public UUID getUUID(String playerName) throws RuntimeException
    {
        Connection connection = getConnection();
        String uuidStr = "[Unknown]";
        try
        {
            PreparedStatement stmt = connection.prepareStatement(Constants.GET_UUID_STMT);
            stmt.setString(1, playerName);
            ResultSet result = stmt.executeQuery();
            if(result.next())
            {
                uuidStr = result.getString("Player_UUID");
            }
            result.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe("Duels experienced an error while attempting to retrieve a UUID from the SQL Database!");
            Bukkit.getLogger().severe(e.toString());
        }
        if(uuidStr.equals("[Unknown]"))
        {
            // TODO: Custom exception
            throw new RuntimeException("UUID could not be located");
        }
        return UUID.fromString(uuidStr);
    }

    @Override
    public String getLastKnownName(UUID uuid)
    {
        Connection connection = getConnection();
        String name = "[Unknown]";
        try
        {
            PreparedStatement stmt = connection.prepareStatement(Constants.GET_NAME_STMT);
            stmt.setString(1, uuid.toString());
            ResultSet result = stmt.executeQuery();
            if(result.next())
            {
                name = result.getString("Last_Known_Name");
            }
            result.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().severe("Duels experienced an error while attempting to retrieve a username from the SQL Database!");
            Bukkit.getLogger().severe(e.toString());
        }
        return name;
    }

    @Override
    public HashMap<UUID, Integer> getTopTenWins()
    {
        // TODO: Sort with SQL because that'll be WAY faster
        return null;
    }

    @Override
    public HashMap<UUID, Integer> getTopTenKills()
    {
        // TODO: Sort with SQL because that'll be WAY faster
        return null;
    }

    private static class Constants
    {
        public static final String CREATE_TABLE_STMT = "CREATE TABLE IF NOT EXISTS duels_player (" +
                "Player_UUID VARCHAR(37) NOT NULL, " +
                "Last_Known_Name VARCHAR(32) NOT NULL, " +
                "Wins INT NOT NULL, " +
                "Losses INT NOT NULL, " +
                "Kills INT NOT NULL, " +
                "Deaths INT NOT NULL, " +
                "PRIMARY KEY ( Player_UUID )" +
                ");";

        public static final String REGISTER_PLAYER_STMT = "INSERT INTO duels_player VALUES (?, ?, 0, 0, 0, 0)";

        public static final String GET_NAME_STMT = "SELECT Last_Known_Name FROM duels_player WHERE Player_UUID = ?";

        public static final String GET_UUID_STMT = "SELECT Player_UUID FROM duels_player WHERE Last_Known_Name = ?";

        public static final String GET_WINS_STMT = "SELECT Wins FROM duels_player WHERE Player_UUID = ?";

        public static final String GET_LOSSES_STMT = "SELECT Losses FROM duels_player WHERE Player_UUID = ?";

        public static final String GET_KILLS_STMT = "SELECT Kills FROM duels_player WHERE Player_UUID = ?";

        public static final String GET_DEATHS_STMT = "SELECT Deaths FROM duels_player WHERE Player_UUID = ?";

        public static final String SET_WINS_STMT = "UPDATE duels_player SET Wins = ? WHERE Player_UUID = ?";

        public static final String SET_LOSSES_STMT = "UPDATE duels_player SET Losses = ? WHERE Player_UUID = ?";

        public static final String SET_KILLS_STMT = "UPDATE duels_player SET Kills = ? WHERE Player_UUID = ?";

        public static final String SET_DEATHS_STMT = "UPDATE duels_player SET Deaths = ? WHERE Player_UUID = ?";
    }
}
