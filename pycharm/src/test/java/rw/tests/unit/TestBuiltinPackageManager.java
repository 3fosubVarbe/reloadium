package rw.tests.unit;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rw.config.Config;
import rw.pkg.Architecture;
import rw.pkg.wheel.BaseWheel;
import rw.tests.BaseMockedTestCase;
import rw.tests.fixtures.PackageFixture;
import rw.tests.utils.MiscUtils;
import rw.util.OsType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class TestBuiltinPackageManager extends BaseMockedTestCase {
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();

    }

    @AfterEach
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testShouldInstallAlreadyInstalled() throws Exception {
        PackageFixture packageFixture = new PackageFixture(this.builtinPackageManager.getBuiltinVersion());
        assertThat(this.builtinPackageManager.shouldInstall()).isFalse();
    }

    @Test
    public void testShouldInstallCurentVersionOld() throws Exception {
        PackageFixture packageFixture = new PackageFixture("0.0.0");
        assertThat(this.builtinPackageManager.shouldInstall()).isTrue();
    }

    @Test
    public void testShouldInstallCurentVersionNewer() throws Exception {
        PackageFixture packageFixture = new PackageFixture("1000.0.0");
        assertThat(this.builtinPackageManager.shouldInstall()).isFalse();
    }

    @Test
    public void testInstalling() throws Exception {
        this.builtinPackageManager.install(null);
        MiscUtils.assertInstalled(this.builtinVersion);
    }
}