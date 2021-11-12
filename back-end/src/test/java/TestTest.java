import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.util.*;

public class TestTest{

  @Test
  public void mockWorkingTest(){
    LinkedList mockedList = mock(LinkedList.class);
    when(mockedList.get(0)).thenReturn(1);

    assertEquals(1,mockedList.get(0));

  }
}
