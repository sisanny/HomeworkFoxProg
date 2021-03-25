package com.greenfoxacademy.homeworkfoxprog.service;

import com.greenfoxacademy.homeworkfoxprog.models.Fox;
import com.greenfoxacademy.homeworkfoxprog.models.Mood;
import com.greenfoxacademy.homeworkfoxprog.models.User;
import com.greenfoxacademy.homeworkfoxprog.repositories.FoxRepository;
import com.greenfoxacademy.homeworkfoxprog.services.FoxService;
import com.greenfoxacademy.homeworkfoxprog.services.FoxServiceImpl;
import com.greenfoxacademy.homeworkfoxprog.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class FoxServiceImplTest {

  FoxService foxServiceMock = mock(FoxService.class);

  UserService userServiceMock = mock(UserService.class);

  FoxRepository foxRepositoryMock = mock(FoxRepository.class);

  FoxServiceImpl foxService = new FoxServiceImpl(foxRepositoryMock);

  //@MockBean
  //FoxRepository foxRepositoryMock;

  //@Autowired
  //FoxService foxService;

  @Test
  void findMyFoxById() {
    Fox foxToFindById = Fox.builder()
                           .id(1)
                           .build();
    User userToFindFoxById = User.builder()
                                 .foxes(Stream.of(new Fox(), foxToFindById)
                                              .collect(Collectors.toList()))
                                 .build();

    when(foxRepositoryMock.findById(1)).thenReturn(foxToFindById);
    when(foxRepositoryMock.findById(4)).thenReturn(null);
    Assertions.assertSame(foxToFindById, foxService.findMyFoxById(1, userToFindFoxById));
    Assertions.assertSame(null, foxService.findMyFoxById(4, userToFindFoxById));
  }

  /*@Test
  void findFoxesOfCurrentUserShouldReturnTheCorrectAmount() {
    User pisti = new User("pisti");
    when(foxRepositoryMock.findAll()).thenReturn(Stream
      .of(new Fox(pisti), new Fox(pisti),
          new Fox(new User("lajos")), new Fox(new User("lilike")))
        .collect(Collectors.toList()));

    int actual = foxService.findFoxesOfCurrentUser(pisti).size();
    Assertions.assertEquals(2, actual);
  }*/

  @Test
  void setNutrition() {
  }

  @Test
  void findFoxByName() {
  }

  @Test
  void scheduledMoodChange() {
    Fox happyFox = Fox.builder()
        .mood(Mood.HAPPY)
        .build();
    Fox okFox = Fox.builder()
        .mood(Mood.OK)
        .build();
    Fox sadFox = Fox.builder()
        .mood(Mood.SAD)
        .build();
    when(foxRepositoryMock.findAll()).thenReturn(Stream
        .of(happyFox, okFox, sadFox)
        .collect(Collectors.toList()));
  }
}