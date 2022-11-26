package login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class UserSession {

    public static void createUserSession(String userName) {
        LocalDateTime dateTime = LocalDateTime.now();
        String session = userName + dateTime;
        DatabaseConnect databaseConnect = new DatabaseConnect();
        String hash_session = databaseConnect.hash(session);
        Path session_path = Paths.get("src/main/resources/session.txt");
        try (
                BufferedWriter session_writer = Files.newBufferedWriter(session_path, StandardCharsets.UTF_8)
        ) {
            DatabaseConnect.getConnect();
            session_writer.write(hash_session);
            DatabaseConnect.createSession(hash_session);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkSession() {
        Path session_path = Paths.get("src/main/resources/session.txt");
        try (
                BufferedReader session_reader = Files.newBufferedReader(session_path, StandardCharsets.UTF_8);
        ) {
            String key = session_reader.readLine();
            if (key != null) {
                DatabaseConnect.getConnect();
                if (DatabaseConnect.checkUserSession()) {
                    return true;

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void deleteUserSession() {

        Path session_path = Paths.get("src/main/resources/session.txt");
        try (
                BufferedWriter session_writer = Files.newBufferedWriter(session_path, StandardCharsets.UTF_8)
        ) {
            checkSession();
            session_writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSession() {
        Path session_path = Paths.get("src/main/resources/session.txt");
        try (
                BufferedReader session_reader = Files.newBufferedReader(session_path, StandardCharsets.UTF_8);
        ) {

            String key= session_reader.readLine();
            if (key != null) {
                return key;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
