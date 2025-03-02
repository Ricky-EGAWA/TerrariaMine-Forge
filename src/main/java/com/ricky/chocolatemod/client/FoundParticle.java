package com.ricky.chocolatemod.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class FoundParticle extends TextureSheetParticle {

    protected FoundParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);
        this.scale(2F);  // サイズ調整
        this.lifetime = 20; // 生存時間（tick単位）
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            FoundParticle particle = new FoundParticle(level, x, y, z, xd, yd, zd);
            particle.pickSprite(sprite);
            return particle;
        }
    }
}
