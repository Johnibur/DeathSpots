/*
 * MIT License
 *
 * Copyright (c) 2020-2023 William Blake Galbreath
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.pl3x.map.deathspots;

import net.pl3x.map.deathspots.listener.BukkitListener;
import net.pl3x.map.deathspots.listener.Pl3xMapListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathSpots extends JavaPlugin {
    private boolean hasHook;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BukkitListener(this), this);

        if (getServer().getPluginManager().isPluginEnabled("Pl3xMap")) {
            getLogger().info("Found Pl3xMap. Hooking into plugin.");
            getServer().getPluginManager().registerEvents(new Pl3xMapListener(), this);
            this.hasHook = true;
        } else {
            getLogger().info("Could not find Pl3xMap. Skipping hook.");
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);

        if (this.hasHook) {
            Pl3xMapListener.shutdown();
        }
    }
}
