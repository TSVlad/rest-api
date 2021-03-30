package com.tsvlad.restapi;

import com.tsvlad.restapi.dao.DataStorage;
import com.tsvlad.restapi.dao.DataStorageImpl;
import com.tsvlad.restapi.entity.PhoneBookEntry;
import com.tsvlad.restapi.entity.User;
import com.tsvlad.restapi.rest.PhoneBookRestController;
import com.tsvlad.restapi.rest.UsersRestController;
import com.tsvlad.restapi.service.PhoneBookService;
import com.tsvlad.restapi.service.PhoneBookServiceImpl;
import com.tsvlad.restapi.service.UserService;
import com.tsvlad.restapi.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Collection;
import java.util.List;

@SpringBootTest
class RestApiApplicationTests {
    private PhoneBookRestController phoneBookRestController;
    private UsersRestController usersRestController;

    @BeforeEach
    void resetData() {
        DataStorage dataStorage = new DataStorageImpl();
        UserService userService = new UserServiceImpl(dataStorage);
        PhoneBookService phoneBookService = new PhoneBookServiceImpl(dataStorage);
        this.usersRestController = new UsersRestController(userService);
        this.phoneBookRestController = new PhoneBookRestController(phoneBookService);
    }

    @Test
    void testGetAllUsers() {
        Collection<User> users = usersRestController.getAllUsers();
        Assertions.assertEquals(users.size(), 3);
    }

    @ParameterizedTest
    @CsvSource({"1,Vladislav", "2,Ivan", "3,Elena"})
    void testGetUserById(long id, String targetName) {
        User user = usersRestController.getUser(id);
        Assertions.assertEquals(user.getName(), targetName);
    }

    @Test
    void testNewUser() {
        usersRestController.saveUser(new User(0, "NewUser"));
        Collection<User> users = usersRestController.getAllUsers();
        Assertions.assertEquals(users.size(), 4);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    void testEditUser(long id) {
        User user = usersRestController.getUser(id);
        user.setName("New name");
        usersRestController.saveUser(user);
        Collection<User> users = usersRestController.getAllUsers();
        Assertions.assertEquals(users.size(), 3);
        User editedUser = usersRestController.getUser(id);
        Assertions.assertEquals(editedUser.getName(), "New name");
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    void testDeleteUser(long id) {
        usersRestController.deleteUser(id);
        Collection<User> users = usersRestController.getAllUsers();
        Assertions.assertEquals(users.size(), 2);
        User user = usersRestController.getUser(id);
        Assertions.assertNull(user);
    }

    @ParameterizedTest
    @CsvSource({"1,3", "2,3", "3,3"})
    void testGetAllEntries(long userId, long targetSize) {
        Collection<PhoneBookEntry> entries = phoneBookRestController.getAllEntriesForUser(userId);
        Assertions.assertEquals(entries.size(), targetSize);
    }

    @ParameterizedTest
    @CsvSource({"1,1,Ivan,89151111111", "1,2,Vladislav,89152222222", "1,3,Elena,89153333333", "2,1,Denis,89154444444", "2,2,Anna,89155555555", "2,3,Alina,89156666666", "3,1,Polina,89157777777", "3,2,Veronika,89158888888", "3,3,Nikita,89159999999"})
    void testGetEntry(long userId, long id, String targetName, String targetNumber) {
        PhoneBookEntry entry = phoneBookRestController.getPhoneBookEntry(userId, id);
        Assertions.assertEquals(entry.getName(), targetName);
        Assertions.assertEquals(entry.getPhoneNumber(), targetNumber);
    }

    @ParameterizedTest
    @CsvSource({"1,4", "2,4", "3,4"})
    void testNewEntry(long userId, long targetSize) {
        phoneBookRestController.saveEntry(userId, new PhoneBookEntry("number", "name"));
        Collection<PhoneBookEntry> entries = phoneBookRestController.getAllEntriesForUser(userId);
        Assertions.assertEquals(entries.size(), targetSize);
        PhoneBookEntry entry = phoneBookRestController.getPhoneBookEntry(userId, targetSize);
        Assertions.assertEquals(entry.getName(), "name");
        Assertions.assertEquals(entry.getPhoneNumber(), "number");
    }

    @ParameterizedTest
    @CsvSource({"1,1", "1,2", "1,3", "2,1", "2,2", "2,3", "3,1", "3,2", "3,3"})
    void testEditEntry(long userId, long id) {
        PhoneBookEntry entry = phoneBookRestController.getPhoneBookEntry(userId, id);
        entry.setName("new name");
        entry.setPhoneNumber("new number");
        phoneBookRestController.saveEntry(userId, entry);
        PhoneBookEntry editedEntry = phoneBookRestController.getPhoneBookEntry(userId, id);
        Assertions.assertEquals(entry.getName(), "new name");
        Assertions.assertEquals(entry.getPhoneNumber(), "new number");
    }

    @ParameterizedTest
    @CsvSource({"1,1", "1,2", "1,3", "2,1", "2,2", "2,3", "3,1", "3,2", "3,3"})
    void testDeleteEntry(long userId, long id) {
        phoneBookRestController.deletePhoneBookEntry(userId, id);
        Assertions.assertNull(phoneBookRestController.getPhoneBookEntry(userId, id));
    }

    @ParameterizedTest
    @CsvSource({"vlad,1", "i,1", "e,1", "unexpected,0"})
    void testGetUsersByName(String input, int targetSize) {
        List<User> users = usersRestController.getUsersByName(input);
        Assertions.assertEquals(users.size(), targetSize);
    }

    @ParameterizedTest
    @CsvSource({"1,89151111111,1,Ivan", "1,89152222222,2,Vladislav", "2,89155555555,2,Anna", "2,89156666666,3,Alina", "3,89157777777,1,Polina", "3,89159999999,3,Nikita"})
    void testGetEntryByNumber(long userId, String number, long targetId, String targetName) {
        PhoneBookEntry entry = phoneBookRestController.getEntryByNumber(userId, number);
        Assertions.assertEquals(entry.getId(), targetId);
        Assertions.assertEquals(entry.getName(), targetName);
    }

    /*@Test
    void contextLoads() {
    }

    @Test
    public void testGetAllUsers() {
        HttpResponse httpResponse = getAll();
        Assertions.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void testNewUser() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String newUserName = "NewUser";


            HttpResponse getAllResponse = getAll();
            Assertions.assertNotNull(getAllResponse);
            Assertions.assertEquals(getAllResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
            String usersBeforeJson = EntityUtils.toString(getAllResponse.getEntity());
            User[] usersBefore = objectMapper.readValue(usersBeforeJson, User[].class);

            HttpPost newUserRequest = new HttpPost("http://localhost:8080/save-user");
            String jsonBody = "{\n" +
                    "    \"id\": 0,\n" +
                    "    \"name\": \"" + newUserName + "\",\n" +
                    "    \"phoneBook\": null\n" +
                    "}";
            HttpResponse response = saveUser(jsonBody);
            Assertions.assertNotNull(response);
            Assertions.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);

            getAllResponse = getAll();
            Assertions.assertEquals(getAllResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
            String usersAfterJson = EntityUtils.toString(getAllResponse.getEntity());
            User[] usersAfter = objectMapper.readValue(usersAfterJson, User[].class);
            Assertions.assertEquals(usersAfter.length, usersBefore.length + 1);

            boolean userAdded = false;
            for (User user : usersAfter) {
                if (user.getName().equals(newUserName) && user.getPhoneBook().getEntries().isEmpty()) {
                    userAdded = true;
                    break;
                }
            }
            Assertions.assertTrue(userAdded);

        } catch (IOException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testGetUser() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            long id = 1;

            HttpResponse getUserResponse = getUser(id);
            Assertions.assertNotNull(getUserResponse);
            String userJson = EntityUtils.toString(getUserResponse.getEntity());
            User user = objectMapper.readValue(userJson, User.class);

            Assertions.assertNotNull(user);
            Assertions.assertEquals(user.getId(), 1);
            Assertions.assertEquals(user.getName(), "Vladislav");

        } catch (IOException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testEditUser() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            HttpResponse getAllResponse = getAll();
            Assertions.assertNotNull(getAllResponse);
            Assertions.assertEquals(getAllResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
            String usersBeforeJson = EntityUtils.toString(getAllResponse.getEntity());
            User[] usersBefore = objectMapper.readValue(usersBeforeJson, User[].class);

            String jsonBody = "{\n" +
                    "        \"id\": 2,\n" +
                    "        \"name\": \"IvanQWE\",\n" +
                    "        \"phoneBook\": {\n" +
                    "            \"currentId\": 4,\n" +
                    "            \"entries\": [\n" +
                    "                {\n" +
                    "                    \"id\": 1,\n" +
                    "                    \"phoneNumber\": \"89154444444\",\n" +
                    "                    \"name\": \"Denis\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"id\": 2,\n" +
                    "                    \"phoneNumber\": \"89155555555\",\n" +
                    "                    \"name\": \"Anna\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"id\": 3,\n" +
                    "                    \"phoneNumber\": \"89156666666\",\n" +
                    "                    \"name\": \"Alina\"\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    }";
            HttpResponse response = saveUser(jsonBody);
            Assertions.assertNotNull(response);
            Assertions.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);

            getAllResponse = getAll();
            Assertions.assertEquals(getAllResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
            String usersAfterJson = EntityUtils.toString(getAllResponse.getEntity());
            User[] usersAfter = objectMapper.readValue(usersAfterJson, User[].class);
            Assertions.assertEquals(usersAfter.length, usersBefore.length);
        } catch (IOException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testDeleteUser() {
        ObjectMapper objectMapper = new ObjectMapper();
        long id = 3;

        try {
            HttpResponse response = deleteUser(3);
            Assertions.assertNotNull(response);
            Assertions.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
            HttpResponse getDeletedUserResponse = getUser(3);
            Assertions.assertNotNull(getDeletedUserResponse);
            String userJson = EntityUtils.toString(getDeletedUserResponse.getEntity());
            Assertions.assertEquals(userJson, "");
        } catch (IOException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testAllEntries() {
        long id = 1;
        ObjectMapper objectMapper = new ObjectMapper();

        HttpResponse httpResponse = getAllEntries(id);
        Assertions.assertNotNull(httpResponse);
        Assertions.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    private HttpResponse getAllEntries(long userId) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet request = new HttpGet("http://localhost:8080/all-entries/" + userId);
            return client.execute(request);
        } catch (IOException e) {
            Assertions.fail(e);
            return null;
        }
    }

    private HttpResponse deleteUser(long id) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpDelete deleteUserRequest = new HttpDelete("http://localhost:8080/delete-user/" + id);
            return client.execute(deleteUserRequest);
        } catch (IOException e) {
            Assertions.fail(e);
            return null;
        }
    }

    private HttpResponse getUser(long id) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet getUserRequest = new HttpGet("http://localhost:8080/user/" + id);
            return client.execute(getUserRequest);
        }catch (IOException e) {
            Assertions.fail(e);
            return null;
        }
    }

    private HttpResponse saveUser(String jsonBody) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost newUserRequest = new HttpPost("http://localhost:8080/save-user");
            StringEntity entity = new StringEntity(jsonBody);
            newUserRequest.setEntity(entity);
            newUserRequest.setHeader("Accept", "application/json");
            newUserRequest.setHeader("Content-type", "application/json");
            return client.execute(newUserRequest);
        } catch (IOException e) {
            Assertions.fail(e);
            return null;
        }
    }

    private HttpResponse getAll() {
        try {
            HttpUriRequest request = new HttpGet("http://localhost:8080/all-users");
            return HttpClientBuilder.create().build().execute(request);
        } catch (IOException e) {
            Assertions.fail(e);
            return null;
        }
    }*/
}
