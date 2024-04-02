package org.polyfrost.polyparticles.mixin.oneconfig;

import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.gui.elements.config.ConfigSlider;
import cc.polyfrost.oneconfig.libs.universal.UChat;
import org.polyfrost.polyparticles.PolyParticles;
import org.polyfrost.polyparticles.config.ModConfig;
import org.polyfrost.polyparticles.config.ParticleEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;
import java.util.Objects;

@Mixin(value = ConfigSlider.class, remap = false)
public class ConfigSliderMixin {

    @Inject(method = "create", at = @At("HEAD"), cancellable = true)
    private static void w(Field field, Object parent, CallbackInfoReturnable<ConfigSlider> cir) {
        if (parent instanceof ParticleEntry) {
            ParticleEntry entry = (ParticleEntry) parent;
            if (PolyParticles.INSTANCE.getUnfair().contains(entry.getId())) {
                Slider slider = field.getAnnotation(Slider.class);
                if (Objects.equals(slider.name(), "Size")) {
                    cir.setReturnValue(new ConfigSlider(field, parent, slider.name(), slider.description(), slider.category(), slider.subcategory(), slider.min(), 1f, slider.step(), slider.instant()));
                }
            }
        }
    }

}
