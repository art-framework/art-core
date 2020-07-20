/*
 * Copyright 2020 ART-Framework Contributors (https://github.com/Silthus/art-framework)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.silthus.art;

import kr.entree.spigradle.annotations.PluginMain;
import net.silthus.art.scheduler.BukkitScheduler;
import net.silthus.art.targets.EntityTarget;
import net.silthus.art.targets.LivingEntityTarget;
import net.silthus.art.targets.OfflinePlayerTarget;
import net.silthus.art.targets.PlayerTarget;
import net.silthus.slib.bukkit.BasePlugin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

@PluginMain
public class ArtPlugin extends BasePlugin {

    @Override
    public void enable() {

        ART.configuration()
                .set(new BukkitScheduler(this, Bukkit.getScheduler()))
                .targets()
                    .add(Entity.class, EntityTarget::new)
                    .add(Player.class, PlayerTarget::new)
                    .add(LivingEntity.class, LivingEntityTarget::new)
                    .add(OfflinePlayer.class, OfflinePlayerTarget::new);
    }

    @Override
    public void disable() {
    }
}
