package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Player;
import de.spaceStudio.server.repository.PlayerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerControllerImplTest {
    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    PlayerControllerImpl playerControllerImpl;

    @Mock
    Player player;

    @Before
    public void setUp() {
        player = Player.builderPlayer().name("bob").password("code11space").buildPlayer();
        player.setId(0);
        MockitoAnnotations.initMocks(this);
    }

    /**
    @Test
    public void testLoginUser() throws Exception {
        playerControllerImpl.addPlayer(player);
        when(playerControllerImpl.authUser(playerRepository.findByName(player.getName()), player)).thenReturn(true);
        when(playerControllerImpl.authUser(playerRepository.findByName(player.getPassword()), player)).thenReturn(true);
        when(playerControllerImpl.authUser(any(), any())).thenReturn(true);
        when(playerRepository.findByName(any()).isPresent()).thenReturn(true);
        when(playerRepository.findByName(anyString())).thenReturn(Optional.of(player));

        String result = playerControllerImpl.loginUser(player);
       // Assert.assertEquals("replaceMeWithExpectedResult", result);
    }
     */


    public void testGetAllPlayers() throws Exception {
        List<Player> result = playerControllerImpl.getAllPlayers();
        Assert.assertEquals(Arrays.<Player>asList(new Player(new Player.PlayerBuilder("name", "password"))), result);
    }


    public void testGetPlayer() throws Exception {
        Player result = playerControllerImpl.getPlayer(Integer.valueOf(0));
        Assert.assertEquals(new Player(new Player.PlayerBuilder("name", "password")), result);
    }

    @Test
    public void testAddPlayer() throws Exception {
        String result = playerControllerImpl.addPlayer(player);
        Assert.assertEquals(HttpStatus.CREATED.toString(), result);
    }


    public void testUpdatePlayer() throws Exception {
        Player result = playerControllerImpl.updatePlayer(new Player(new Player.PlayerBuilder("name", "password")));
        Assert.assertEquals(new Player(new Player.PlayerBuilder("name", "password")), result);
    }


    public void testDeletePlayerById() throws Exception {
        String result = playerControllerImpl.deletePlayerById(Integer.valueOf(0));
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }


    public void testDeleteAllPlayers() throws Exception {
        String result = playerControllerImpl.deleteAllPlayers();
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }


    public void testGetLoggedPlayers() throws Exception {
        List<String> result = playerControllerImpl.getLoggedPlayers();
        Assert.assertEquals(Arrays.<String>asList("String"), result);
    }


    public void testLogoutUser() throws Exception {
        playerControllerImpl.logoutUser(new Player(new Player.PlayerBuilder("name", "password")));
    }


    public void testHashPassword() throws Exception {
        String result = playerControllerImpl.hashPassword("weakPassword");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }


    public void testAuthUser() throws Exception {
        boolean result = playerControllerImpl.authUser(null, new Player(new Player.PlayerBuilder("name", "password")));
        Assert.assertEquals(true, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme