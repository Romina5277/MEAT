package ch.axa.meatbackend.inputvalidation;

import ch.axa.meatbackend.mongo.model.*;
import ch.axa.meatbackend.mongo.repository.ApplicationRepository;
import ch.axa.meatbackend.service.impl.ApplicationServiceImpl;
import ch.axa.meatbackend.service.impl.ServiceUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class InputValidationServices {

    @Mock
    private ApplicationRepository mockApplicationRepository;
    @Mock
    private Application mockApp;

    private ApplicationServiceImpl applicationService;

    private static final String APP_ID = "5f1551f91af5a16e3291c489";
    private static final String LOCATION_ID = "5f16cb77dd095c04364f5fcc";
    private static final String TICKER_ID = "5f155c4a4ff0be657a23f2e9";
    private static final String MESSAGE_ID = "5f155c4a4ff0be657a23f2e9";
    private static final String TEMPLATE_ID = "5f15520d1af5a16e3291c48a";

    @Before
    public void before() {
        applicationService = spy(new ApplicationServiceImpl(mockApplicationRepository));

        when(mockApp.getId()).thenReturn(APP_ID);
        Location mockExistingLocation = mock(Location.class);
        when(mockExistingLocation.getId()).thenReturn(LOCATION_ID);

        Message mockMessage = mock(Message.class);
        when(mockMessage.getId()).thenReturn(MESSAGE_ID);

        Ticker mockTicker = mock(Ticker.class);
        when(mockTicker.getId()).thenReturn(TICKER_ID);
        when(mockTicker.getMessages()).thenReturn(Collections.singletonList(mockMessage));

        Ticker mockTemplate = mock(Ticker.class);
        when(mockTemplate.getId()).thenReturn(TEMPLATE_ID);
        when(mockTemplate.getMessages()).thenReturn(Collections.singletonList(mockMessage));

        when(mockApp.getLocations()).thenReturn(Collections.singletonList(mockExistingLocation));
        when(mockApp.getTickers()).thenReturn(Collections.singletonList(mockTicker));
        when(mockApp.getTemplates()).thenReturn(Collections.singletonList(mockTemplate));

    }

    @Test
    public void testApplicationInput_validApplicationWithNewCode() {
        Application newApp = new Application("GO", "Grouplife Offerten");

        when(applicationService.findByCode(anyString())).thenReturn(Optional.empty());

        boolean validApplication = applicationService.isApplicationInputValid(newApp);

        verify(mockApplicationRepository, times(1)).findByCode(newApp.getCode());
        assertTrue(validApplication);

    }

    @Test
    public void testApplicationInput_validApplicationEdit() {
        Application newApp = new Application("GO", "Grouplife Offerten Edited");
        newApp.setId("5f1551f91af5a16e3291c489");

        Application existingFromDB = new Application();
        existingFromDB.setId("5f1551f91af5a16e3291c489");

        when(applicationService.findByCode(anyString())).thenReturn(Optional.of(existingFromDB));

        boolean validApplication = applicationService.isApplicationInputValid(newApp);

        verify(mockApplicationRepository, times(1)).findByCode(newApp.getCode());
        assertTrue(validApplication);
    }

    @Test
    public void testApplicationInput_emptyCode() {
        Application newApp = new Application(null, "Grouplife Offerten Edited");

        boolean validApplication = applicationService.isApplicationInputValid(newApp);

        verify(mockApplicationRepository, never()).findByCode(anyString());
        assertFalse(validApplication);
    }

    @Test
    public void testApplicationInput_emptyName() {
        Application newApp = new Application("GO", null);

        boolean validApplication = applicationService.isApplicationInputValid(newApp);

        verify(mockApplicationRepository, never()).findByCode(anyString());
        assertFalse(validApplication);
    }

    @Test
    public void testApplicationInput_xmlInvalidCode() {
        Application newApp = new Application("<GO>", "Grouplife Offerten");

        boolean validApplication = applicationService.isApplicationInputValid(newApp);

        verify(mockApplicationRepository, never()).findByCode(anyString());
        assertFalse(validApplication);
    }

    @Test
    public void testApplicationInput_xmlInvalidName() {
        Application newApp = new Application("GO", "&<Grouplife Offerten");

        boolean validApplication = applicationService.isApplicationInputValid(newApp);

        verify(mockApplicationRepository, never()).findByCode(anyString());
        assertFalse(validApplication);
    }

    @Test
    public void testApplicationInput_nameTooLong() {
        Application newApp = new Application("This Code Is too Long", "Grouplife Offerten");

        boolean validApplication = applicationService.isApplicationInputValid(newApp);

        verify(mockApplicationRepository, never()).findByCode(anyString());
        assertFalse(validApplication);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testApplicationInput_codeIsAlreadyAssigned() {
        Application editedApp = new Application("GO", "Grouplife Offerten Edited");
        editedApp.setId("5f1551f91af5a16e3291c489");

        Application existingFromDB = new Application();
        existingFromDB.setId("1e1551f91af5a16e3291d234");

        when(mockApplicationRepository.findByCode(anyString())).thenReturn(Optional.of(existingFromDB));
        applicationService.isApplicationInputValid(editedApp);
        verify(mockApplicationRepository, times(1)).findByCode(editedApp.getCode());
    }

    @Test
    public void testLocationInput_validNewLocation() {
        Location newLocation = new Location("Login-Screen", 1);

        boolean valid = applicationService.isLocationInputValid(newLocation, APP_ID, false);

        verify(mockApplicationRepository, never()).findById(anyString());
        assertTrue(valid);
    }

    @Test
    public void testLocationInput_validEditLocation() {
        Location location = new Location("Login-Screen", 1);
        location.setId(LOCATION_ID);

        when(mockApplicationRepository.findById(anyString())).thenReturn(Optional.of(mockApp));
        boolean valid = applicationService.isLocationInputValid(location, APP_ID, true);

        verify(mockApplicationRepository, times(1)).findById(APP_ID);
        assertTrue(valid);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLocationInput_editLocationNotFound() {
        Location location = new Location("Login-Screen", 1);
        location.setId("5f155c4a4ff0be657a23f2e8");

        when(mockApplicationRepository.findById(anyString())).thenReturn(Optional.of(mockApp));
        boolean valid = applicationService.isLocationInputValid(location, APP_ID, true);

        verify(mockApplicationRepository, times(1)).findById(APP_ID);
        assertFalse(valid);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLocationInput_editLocationNoParentApplicationFound() {
        Location location = new Location("Login-Screen", 1);
        location.setId("5f16cb77dd095c04364f5fcc");

        when(mockApplicationRepository.findById(anyString())).thenReturn(Optional.empty());
        boolean valid = applicationService.isLocationInputValid(location, null, true);

        verify(mockApplicationRepository, times(1)).findById(null);
        assertFalse(valid);
    }

    @Test
    public void testLocationInput_newLocationEmptyName() {
        Location newLocation = new Location(null, 1);

        boolean valid = applicationService.isLocationInputValid(newLocation, APP_ID, false);

        verify(mockApplicationRepository, never()).findByCode(any());
        assertFalse(valid);
    }

    @Test
    public void testLocationInput_newLocationInvalidLocationNumber() {
        Location newLocation = new Location("Login-Page", 0);

        boolean valid = applicationService.isLocationInputValid(newLocation, APP_ID, false);

        verify(mockApplicationRepository, never()).findByCode(any());
        assertFalse(valid);
    }

    @Test
    public void testLocationInput_newLocationXmlInvalidName() {
        Location newLocation = new Location("</Login-Page>", 1);

        boolean valid = applicationService.isLocationInputValid(newLocation, APP_ID, false);

        verify(mockApplicationRepository, never()).findByCode(any());
        assertFalse(valid);
    }

    @Test
    public void testLocationInput_editLocationEmptyName() {
        Location editLocation = new Location(null, 1);
        editLocation.setId(LOCATION_ID);

        when(mockApplicationRepository.findById(anyString())).thenReturn(Optional.of(mockApp));
        boolean valid = applicationService.isLocationInputValid(editLocation, APP_ID, true);

        verify(mockApplicationRepository, never()).findByCode(any());
        assertFalse(valid);
    }

    @Test
    public void testLocationInput_editLocationInvalidLocationNumber() {
        Location editLocation = new Location("Login-Page", 0);
        editLocation.setId(LOCATION_ID);

        when(mockApplicationRepository.findById(anyString())).thenReturn(Optional.of(mockApp));
        boolean valid = applicationService.isLocationInputValid(editLocation, APP_ID, true);

        verify(mockApplicationRepository, never()).findByCode(any());
        assertFalse(valid);
    }

    @Test
    public void testLocationInput_editLocationXmlInvalidName() {
        Location editLocation = new Location("</Login-Page>", 1);
        editLocation.setId(LOCATION_ID);

        when(mockApplicationRepository.findById(anyString())).thenReturn(Optional.of(mockApp));
        boolean valid = applicationService.isLocationInputValid(editLocation, APP_ID, true);

        verify(mockApplicationRepository, never()).findByCode(any());
        assertFalse(valid);
    }

    @Test
    public void testTickerInput_newTicker_valid() {
        Message newMsg = new Message("Es gibt schulungen", "de", "Schulungen");
        Ticker newTicker = new Ticker("Schulungen", Arrays.asList(1, 2), "2020-03-04 08:00:00", "2020-04-04 08:00:00", Collections.singletonList(newMsg));

        doReturn(false).when(applicationService).checkApplicationExistence(anyString());
        doReturn(true).when(applicationService).isMessageInputOfTickerValid(any(Message.class), anyString(), anyString(), anyBoolean());

        boolean valid = applicationService.isTickerInputValid(newTicker, APP_ID, false);

        verify(mockApplicationRepository, never()).findById(anyString());
        assertTrue(valid);
    }

    @Test
    public void testTickerInput_editTicker_valid() {
        Message editMsg = new Message("Es gibt schulungen", "de", "Schulungen");
        editMsg.setId(MESSAGE_ID);
        Ticker editTicker = new Ticker("Schulungen", Arrays.asList(1, 2), "2020-03-04 08:00:00", "2020-04-04 08:00:00", Collections.singletonList(editMsg));
        editTicker.setId(TICKER_ID);

        doReturn(false).when(applicationService).checkApplicationExistence(anyString());
        doReturn(true).when(applicationService).isMessageInputOfTickerValid(any(Message.class), anyString(), anyString(), anyBoolean());
        when(mockApplicationRepository.findById(anyString())).thenReturn(Optional.empty());

        boolean valid = applicationService.isTickerInputValid(editTicker, APP_ID, true);

        verify(mockApplicationRepository, never()).findById(anyString());
        assertTrue(valid);
    }

    @Test
    public void testTickerInput_newTicker_emptyTitle() {
        Ticker editTicker = new Ticker(null, Arrays.asList(1, 2), "2020-03-04 08:00:00", "2020-04-04 08:00:00", new ArrayList<>());

        doReturn(false).when(applicationService).checkApplicationExistence(anyString());
        boolean valid = applicationService.isTickerInputValid(editTicker, APP_ID, false);

        verify(applicationService, never()).checkApplicationExistence(anyString());
        assertFalse(valid);
    }

    @Test
    public void testTickerInput_newTicker_emptyTimeFrom() {
        Ticker editTicker = new Ticker("Schulungen", Arrays.asList(1, 2), null, "2020-04-04 08:00:00", new ArrayList<>());

        doReturn(false).when(applicationService).checkApplicationExistence(anyString());
        boolean valid = applicationService.isTickerInputValid(editTicker, APP_ID, false);

        verify(applicationService, never()).checkApplicationExistence(anyString());
        assertFalse(valid);
    }

    @Test
    public void testTickerInput_newTicker_emptyTimeTo() {
        Ticker editTicker = new Ticker("Schulungen", Arrays.asList(1, 2), "2020-03-04 08:00:00", null, new ArrayList<>());

        doReturn(false).when(applicationService).checkApplicationExistence(anyString());
        boolean valid = applicationService.isTickerInputValid(editTicker, APP_ID, false);

        verify(applicationService, never()).checkApplicationExistence(anyString());
        assertFalse(valid);
    }

    @Test
    public void testTickerInput_newTicker_noLocationNumbers() {
        Ticker editTicker = new Ticker("Schulungen", new ArrayList<>(), "2020-03-04 08:00:00", "2020-04-04 08:00:00", new ArrayList<>());

        doReturn(false).when(applicationService).checkApplicationExistence(anyString());
        boolean valid = applicationService.isTickerInputValid(editTicker, APP_ID, false);

        verify(applicationService, never()).checkApplicationExistence(anyString());
        assertFalse(valid);
    }

    @Test
    public void testTickerInput_newTicker_xmlInvalidTitle() {
        Ticker editTicker = new Ticker("<Schulungen&>", Arrays.asList(1, 2), "2020-03-04 08:00:00", "2020-04-04 08:00:00", new ArrayList<>());

        doReturn(false).when(applicationService).checkApplicationExistence(anyString());
        boolean valid = applicationService.isTickerInputValid(editTicker, APP_ID, false);

        verify(applicationService, never()).checkApplicationExistence(anyString());
        assertFalse(valid);
    }

    @Test
    public void testTickerInput_newTicker_invalidTime() {
        Ticker editTicker = new Ticker("Schulungen", Arrays.asList(1, 2), "2020-04-04 08:00:00", "2020-03-04 08:00:00", new ArrayList<>());

        doReturn(false).when(applicationService).checkApplicationExistence(anyString());
        boolean valid = applicationService.isTickerInputValid(editTicker, APP_ID, false);

        verify(applicationService, never()).checkApplicationExistence(anyString());
        assertFalse(valid);
    }

    @Test
    public void testMessageInput_ofTicker_validNewMessage() {
        Message newMsg = new Message("Es gibt schulungen", "de", "Schulungen");

        assertTrue(applicationService.isMessageInputOfTickerValid(newMsg, APP_ID, TICKER_ID, false));
    }

    @Test
    public void testMessageInput_ofTicker_validEditedMessage() {
        Message newMsg = new Message("Es gibt schulungen", "de", "Schulungen");
        newMsg.setId(MESSAGE_ID);

        when(mockApplicationRepository.findById(anyString())).thenReturn(Optional.of(mockApp));

        assertTrue(applicationService.isMessageInputOfTickerValid(newMsg, APP_ID, TICKER_ID, true));
        verify(mockApplicationRepository, times(1)).findById(anyString());
    }

    @Test
    public void testMessageInput_ofTemplate_validNewMessage() {
        Message newMsg = new Message("Es gibt schulungen", "de", "Schulungen");

        assertTrue(applicationService.isMessageInputOfTemplateValid(newMsg, APP_ID, TICKER_ID, false));
    }

    @Test
    public void testMessageInput_ofTemplate_validEditedMessage() {
        Message newMsg = new Message("Es gibt schulungen", "de", "Schulungen");
        newMsg.setId(MESSAGE_ID);

        when(mockApplicationRepository.findById(anyString())).thenReturn(Optional.of(mockApp));

        assertTrue(applicationService.isMessageInputOfTemplateValid(newMsg, APP_ID, TEMPLATE_ID, true));
        verify(mockApplicationRepository, times(1)).findById(anyString());
    }

    @Test
    public void testMessageInput_ofTicker_emptyTitle() {
        Message newMsg = new Message("Es gibt schulungen", "de", null);

        assertFalse(applicationService.isMessageInputOfTickerValid(newMsg, APP_ID, TICKER_ID, false));
    }

    @Test
    public void testMessageInput_ofTicker_emptyLanguage() {
        Message newMsg = new Message("Es gibt schulungen", null, "Schulungen");

        assertFalse(applicationService.isMessageInputOfTickerValid(newMsg, APP_ID, TICKER_ID, false));
    }

    @Test
    public void testMessageInput_ofTicker_emptyText() {
        Message newMsg = new Message(null, "de", "Schulungen");

        assertFalse(applicationService.isMessageInputOfTickerValid(newMsg, APP_ID, TICKER_ID, false));
    }

    @Test
    public void testMessageInput_ofTicker_xmlInvalidTitle() {
        Message newMsg = new Message("Es gibt schulungen", "de", "<Schulungen>");

        assertFalse(applicationService.isMessageInputOfTickerValid(newMsg, APP_ID, TICKER_ID, false));
    }

    @Test
    public void testMessageInput_ofTicker_xmlInvalidText() {
        Message newMsg = new Message("<Es gibt schulungen>", "de", "Schulungen");

        assertFalse(applicationService.isMessageInputOfTickerValid(newMsg, APP_ID, TICKER_ID, false));
    }

    @Test
    public void testMessageInput_ofTicker_invalidLanguage() {
        Message newMsg = new Message("Es gibt schulungen", "ed", "Schulungen");

        assertFalse(applicationService.isMessageInputOfTickerValid(newMsg, APP_ID, TICKER_ID, false));
    }

    @Test
    public void testXMLInvalideSymbols_withInvalidSymbols() {
        // XML invalide Zeichen im String
        String xmlTestString = "Invalider&String";
        assertTrue(ServiceUtils.hasXMLInvalideSymbol(xmlTestString));
    }

    @Test
    public void testXMLInvalideSymbols_withoutInvalidSymbols() {
        String xmlTestString = "Valider String";
        assertFalse(ServiceUtils.hasXMLInvalideSymbol(xmlTestString));
    }

    @Test
    public void testTime_bothValid() {
        String timeFromValid = "2020-03-04 08:00:00";
        String timeToValid = "2020-04-04 08:00:00";

        assertFalse(ServiceUtils.timeValidate(timeToValid, timeFromValid));
        assertTrue(ServiceUtils.timeValidate(timeFromValid, timeToValid));
    }

    @Test
    public void testTime_bothInvalid() {
        String timeFromInvalid = "04-03-2020 08:00:00";
        String timeToInvalid = "04-04-2020 08:00:00";

        assertFalse(ServiceUtils.timeValidate(timeFromInvalid, timeToInvalid));

    }

    @Test
    public void testTime_onlyTimeFromIsValid() {
        String timeFromValid = "2020-03-04 08:00:00";
        String timeToInvalid = "04-04-2020 08:00:00";

        assertFalse(ServiceUtils.timeValidate(timeFromValid, timeToInvalid));

    }

    @Test
    public void testTime_onlyTimeToIsValid() {
        String timeToValid = "2020-04-04 08:00:00";
        String timeFromInvalid = "04-03-2020 08:00:00";

        assertFalse(ServiceUtils.timeValidate(timeFromInvalid, timeToValid));
    }

    @Test
    public void testTime_TimeFromIsAfterTimeTo() {
        String timeFromValid = "2020-04-04 08:00:00";
        String timeToValid = "2020-03-04 08:00:00";

        assertFalse(ServiceUtils.timeValidate(timeFromValid, timeToValid));
    }
}
