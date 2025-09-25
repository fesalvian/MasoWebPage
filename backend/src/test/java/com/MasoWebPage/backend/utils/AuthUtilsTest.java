package com.MasoWebPage.backend.utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthUtilsTest {

    @InjectMocks
    private AuthUtils authUtils;

    @Test
    void getAuthentication_ShouldReturnAuthenticationFromSecurityContext() {
        // Arrange
        Authentication expectedAuthentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        try (MockedStatic<SecurityContextHolder> securityContextHolder = mockStatic(SecurityContextHolder.class)) {
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(expectedAuthentication);

            // Act
            Authentication result = authUtils.getAuthenticaion();

            // Assert
            assertNotNull(result);
            assertEquals(expectedAuthentication, result);
            verify(securityContext).getAuthentication();
        }
    }

    @Test
    void isADM_WhenUserHasADMRole_ShouldReturnTrue() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        // Criar uma coleção com o tipo correto
        Collection<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("ADM")
        );

        try (MockedStatic<SecurityContextHolder> securityContextHolder = mockStatic(SecurityContextHolder.class)) {
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getAuthorities()).thenReturn((Collection) authorities);

            // Act
            Boolean result = authUtils.isADM();

            // Assert
            assertTrue(result);
        }
    }

    @Test
    void isADM_WhenUserDoesNotHaveADMRole_ShouldReturnFalse() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        Collection<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("USER")
        );

        try (MockedStatic<SecurityContextHolder> securityContextHolder = mockStatic(SecurityContextHolder.class)) {
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getAuthorities()).thenReturn((Collection) authorities);

            // Act
            Boolean result = authUtils.isADM();

            // Assert
            assertFalse(result);
        }
    }

    @Test
    void isADM_WhenUserHasMultipleRolesIncludingADM_ShouldReturnTrue() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        Collection<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("ADM"),
                new SimpleGrantedAuthority("USER")
        );

        try (MockedStatic<SecurityContextHolder> securityContextHolder = mockStatic(SecurityContextHolder.class)) {
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getAuthorities()).thenReturn((Collection) authorities);

            // Act
            Boolean result = authUtils.isADM();

            // Assert
            assertTrue(result);
        }
    }


    @Test
    void isADM_WhenEmptyAuthorities_ShouldReturnFalse() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        Collection<GrantedAuthority> authorities = Collections.emptyList();

        try (MockedStatic<SecurityContextHolder> securityContextHolder = mockStatic(SecurityContextHolder.class)) {
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getAuthorities()).thenReturn((Collection) authorities);

            // Act
            Boolean result = authUtils.isADM();

            // Assert
            assertFalse(result);
        }
    }

    @Test
    void notADM_WhenUserDoesNotHaveADMRole_ShouldReturnTrue() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        Collection<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("USER")
        );

        try (MockedStatic<SecurityContextHolder> securityContextHolder = mockStatic(SecurityContextHolder.class)) {
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getAuthorities()).thenReturn((Collection) authorities);

            // Act
            Boolean result = authUtils.notADM();

            // Assert
            assertTrue(result);
        }
    }

    @Test
    void notADM_WhenUserHasADMRole_ShouldReturnFalse() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        Collection<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("ADM")
        );

        try (MockedStatic<SecurityContextHolder> securityContextHolder = mockStatic(SecurityContextHolder.class)) {
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getAuthorities()).thenReturn((Collection) authorities);

            // Act
            Boolean result = authUtils.notADM();

            // Assert
            assertFalse(result);
        }
    }


    @Test
    void notADM_WhenEmptyAuthorities_ShouldReturnTrue() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        Collection<GrantedAuthority> authorities = Collections.emptyList();

        try (MockedStatic<SecurityContextHolder> securityContextHolder = mockStatic(SecurityContextHolder.class)) {
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getAuthorities()).thenReturn((Collection) authorities);

            // Act
            Boolean result = authUtils.notADM();

            // Assert
            assertTrue(result);
        }
    }

    @Test
    void isADMAndNotADM_ShouldBeOpposites() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        Collection<GrantedAuthority> authoritiesWithADM = Arrays.asList(
                new SimpleGrantedAuthority("ADM")
        );

        Collection<GrantedAuthority> authoritiesWithoutADM = Arrays.asList(
                new SimpleGrantedAuthority("USER")
        );

        try (MockedStatic<SecurityContextHolder> securityContextHolder = mockStatic(SecurityContextHolder.class)) {
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // Teste com ADM
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getAuthorities()).thenReturn((Collection) authoritiesWithADM);

            assertTrue(authUtils.isADM());
            assertFalse(authUtils.notADM());

            // Teste sem ADM
            when(authentication.getAuthorities()).thenReturn((Collection) authoritiesWithoutADM);

            assertFalse(authUtils.isADM());
            assertTrue(authUtils.notADM());
        }
    }

    @Test
    void isADM_WhenRoleCaseSensitive_ShouldWorkCorrectly() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        // Testando com "adm" em minúsculo (deve retornar false se a Role.ADM for "ADM")
        Collection<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("adm") // minúsculo
        );

        try (MockedStatic<SecurityContextHolder> securityContextHolder = mockStatic(SecurityContextHolder.class)) {
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getAuthorities()).thenReturn((Collection) authorities);

            // Act
            Boolean result = authUtils.isADM();

            // Assert - isso depende de como a Role.ADM está definida
            // Se Role.ADM.toString() retorna "ADM", então deve retornar false
            assertFalse(result);
        }
    }
}