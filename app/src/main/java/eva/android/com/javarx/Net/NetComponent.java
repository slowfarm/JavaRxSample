package eva.android.com.javarx.Net;

import javax.inject.Singleton;

import dagger.Component;
import eva.android.com.javarx.MainActivity;

@Singleton
@Component(modules = {NetModule.class})
public interface NetComponent {
    void inject(MainActivity activity);
}