/*
 * Copyright (C) 2021 legoatoom
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.legoatoom.gameblocks.gameblocks;

import com.legoatoom.gameblocks.gameblocks.blocks.ChessBoardBlock;
import com.legoatoom.gameblocks.gameblocks.blocks.entity.ChessBoardBlockEntity;
import com.legoatoom.gameblocks.gameblocks.screen.ChessBoardScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Function;

public class GameBlocks implements ModInitializer {

    public static final String MOD_ID = "gameblocks";
    public static final Function<String, Identifier> id = (String path) -> new Identifier(MOD_ID, path);
    public static Block CHESS_BOARD_BLOCK;
    public static Item CHESS_BOARD_ITEM;
    public static BlockEntityType<ChessBoardBlockEntity> CHESS_BOARD_BLOCK_ENTITY;
    public static ScreenHandlerType<ChessBoardScreenHandler> CHESS_BOARD_SCREEN_HANDLER;

    static {
        CHESS_BOARD_BLOCK = new ChessBoardBlock();
        CHESS_BOARD_ITEM = new BlockItem(CHESS_BOARD_BLOCK, new FabricItemSettings().group(ItemGroup.DECORATIONS));
        CHESS_BOARD_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(ChessBoardBlockEntity::new, CHESS_BOARD_BLOCK).build();
        CHESS_BOARD_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(id.apply("chess_board"), ChessBoardScreenHandler::new);
    }

    @Override
    public void onInitialize() {
        registerBlocks();
        registerBlocksEntities();
        registerItems();
    }

    private void registerItems() {
        Registry.register(Registry.ITEM, id.apply("chess_board"), CHESS_BOARD_ITEM);
    }

    private void registerBlocksEntities() {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, id.apply("chess_board_entity"), CHESS_BOARD_BLOCK_ENTITY);
    }

    private void registerBlocks() {
        Registry.register(Registry.BLOCK, id.apply("chess_board"), CHESS_BOARD_BLOCK);
    }
}
