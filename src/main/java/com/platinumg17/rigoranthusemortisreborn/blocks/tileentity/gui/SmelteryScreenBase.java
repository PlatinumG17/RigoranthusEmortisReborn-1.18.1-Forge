package com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.gui;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.blocks.tileentity.container.MasterfulSmelteryContainerBase;
import com.platinumg17.rigoranthusemortisreborn.core.init.network.messages.Messages;
import com.platinumg17.rigoranthusemortisreborn.core.init.network.messages.PacketSettingsButton;
import com.platinumg17.rigoranthusemortisreborn.core.init.network.messages.PacketShowSettingsButton;
import com.platinumg17.rigoranthusemortisreborn.util.StringHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

import java.util.List;

/**
 * @author Qelifern
 * https://github.com/Qelifern/IronFurnaces
 * */
@OnlyIn(Dist.CLIENT)
public abstract class SmelteryScreenBase<T extends MasterfulSmelteryContainerBase> extends AbstractContainerScreen<T> {

    public ResourceLocation GUI = RigoranthusEmortisReborn.rl("textures/gui/smeltery.png");
    public static final ResourceLocation WIDGETS = RigoranthusEmortisReborn.rl("textures/gui/widgets.png");
    Inventory playerInv;
    Component name;

    public boolean add_button;
    public boolean sub_button;

    public SmelteryScreenBase(T t, Inventory inv, Component name) {
        super(t, inv, name);
        playerInv = inv;
        this.name = name;
    }

    @Override
    public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrix);
        super.render(matrix, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrix, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderLabels(PoseStack matrix, int mouseX, int mouseY) {
        this.minecraft.font.draw(matrix, this.playerInv.getDisplayName(), 7, this.getYSize() - 93, 4210752);
        this.minecraft.font.draw(matrix, name, 7 + (float)(this.getXSize() / 2) - (float) (this.minecraft.font.width(name.getString()) / 2), 6, 4210752);

        if (this.getMenu().showInventoryButtons() && this.getMenu().getRedstoneMode() == 4) {
            int comSub = this.getMenu().getComSub();
            int i = comSub > 9 ? 28 : 31;
            this.minecraft.font.draw(matrix, new TextComponent("" + comSub), i - 42, 90, 4210752);
        }
        int actualMouseX = mouseX - ((this.width - this.getXSize()) / 2);
        int actualMouseY = mouseY - ((this.height - this.getYSize()) / 2);

        this.addTooltips(matrix, actualMouseX, actualMouseY);
    }

    private void addTooltips(PoseStack matrix, int mouseX, int mouseY) {

        if (!getMenu().showInventoryButtons()) {
            if (mouseX >= -20 && mouseX <= 0 && mouseY >= 4 && mouseY <= 26) {
                this.renderTooltip(matrix, new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_open"), mouseX, mouseY);
            }
        } else {
            if (mouseX >= -13 && mouseX <= 0 && mouseY >= 4 && mouseY <= 26) {
                this.renderComponentTooltip(matrix, StringHelper.getShiftInfoGui(), mouseX, mouseY);
            } else if (mouseX >= -47 && mouseX <= -34 && mouseY >= 12 && mouseY <= 25) {
                List<Component> list = Lists.newArrayList();
                list.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_auto_input"));
                list.add(new TextComponent("" + this.getMenu().getAutoInput()));
                this.renderComponentTooltip(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -29 && mouseX <= -16 && mouseY >= 12 && mouseY <= 25) {
                List<Component> list = Lists.newArrayList();
                list.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_auto_output"));
                list.add(new TextComponent("" + this.getMenu().getAutoOutput()));
                this.renderComponentTooltip(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -32 && mouseX <= -23 && mouseY >= 31 && mouseY <= 40) {
                List<Component> list = Lists.newArrayList();
                list.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_top"));
                list.add(this.getMenu().getTooltip(1));
                this.renderComponentTooltip(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -32 && mouseX <= -23 && mouseY >= 55 && mouseY <= 64) {
                List<Component> list = Lists.newArrayList();
                list.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_bottom"));
                list.add(this.getMenu().getTooltip(0));
                this.renderComponentTooltip(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -32 && mouseX <= -23 && mouseY >= 43 && mouseY <= 52) {
                List<Component> list = Lists.newArrayList();
                if (isShiftKeyDown()) {
                    list.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_reset"));
                } else {
                    list.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_front"));
                    list.add(this.getMenu().getTooltip(this.getMenu().getIndexFront()));
                }
                this.renderComponentTooltip(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -44 && mouseX <= -35 && mouseY >= 43 && mouseY <= 52) {
                List<Component> list = Lists.newArrayList();
                list.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_left"));
                list.add(this.getMenu().getTooltip(this.getMenu().getIndexLeft()));
                this.renderComponentTooltip(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -20 && mouseX <= -11 && mouseY >= 43 && mouseY <= 52) {
                List<Component> list = Lists.newArrayList();
                list.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_right"));
                list.add(this.getMenu().getTooltip(this.getMenu().getIndexRight()));
                this.renderComponentTooltip(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -20 && mouseX <= -11 && mouseY >= 55 && mouseY <= 64) {
                List<Component> list = Lists.newArrayList();
                list.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_back"));
                list.add(this.getMenu().getTooltip(this.getMenu().getIndexBack()));
                this.renderComponentTooltip(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -47 && mouseX <= -34 && mouseY >= 70 && mouseY <= 83) {
                List<Component> list = Lists.newArrayList();
                list.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_redstone_ignored"));
                this.renderComponentTooltip(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -31 && mouseX <= -18 && mouseY >= 70 && mouseY <= 83) {
                List<Component> list = Lists.newArrayList();
                if (isShiftKeyDown()) {
                    list.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_redstone_low"));
                } else {
                    list.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_redstone_high"));
                }
                this.renderComponentTooltip(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -15 && mouseX <= -2 && mouseY >= 70 && mouseY <= 83) {
                List<Component> list = Lists.newArrayList();
                list.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_redstone_comparator"));
                this.renderComponentTooltip(matrix, list, mouseX, mouseY);
            } else if (mouseX >= -47 && mouseX <= -34 && mouseY >= 86 && mouseY <= 99) {
                List<Component> list = Lists.newArrayList();
                list.add(new TranslatableComponent("tooltip." + RigoranthusEmortisReborn.MOD_ID + ".gui_redstone_comparator_sub"));
                this.renderComponentTooltip(matrix, list, mouseX, mouseY);
            }
        }
    }

    @Override
    protected void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.getXSize()) / 2;
        int relY = (this.height - this.getYSize()) / 2;
        this.blit(matrix, relX, relY, 0, 0, this.getXSize(), this.getYSize());
        int i;

        i = ((MasterfulSmelteryContainerBase) this.getMenu()).getBurnLeftScaled(14);
        //while (((MasterfulSmelteryContainerBase) this.getMenu()).isBurning()) {
        this.blit(matrix, getGuiLeft() + 57, getGuiTop() + 37 + 12 - i, 176, 12 - i, 14, i + 2);
        //}
        i = ((MasterfulSmelteryContainerBase) this.getMenu()).getCookScaled(24);
        this.blit(matrix, getGuiLeft() + 79, getGuiTop() + 34, 176, 14, i + 1, 16);

        RenderSystem.setShaderTexture(0, WIDGETS);
        int actualMouseX = mouseX - ((this.width - this.getXSize()) / 2);
        int actualMouseY = mouseY - ((this.height - this.getYSize()) / 2);

        this.addInventoryButtons(matrix, actualMouseX, actualMouseY);
        this.addRedstoneButtons(matrix, actualMouseX, actualMouseY);
    }

    private void addRedstoneButtons(PoseStack matrix, int mouseX, int mouseY) {
        if (this.getMenu().showInventoryButtons()) {
            this.blitRedstone(matrix);
            if (this.getMenu().getRedstoneMode() == 4) {
                int comSub = getMenu().getComSub();
                boolean flag = isShiftKeyDown();
                if (flag) {
                    if (comSub > 0) {
                        this.sub_button = true;
                        if (mouseX >= -31 && mouseX <= -18 && mouseY >= 86 && mouseY <= 99) {
                            this.blit(matrix, getGuiLeft() - 31, getGuiTop() + 86, 14, 0, 14, 14);
                        } else {
                            this.blit(matrix, getGuiLeft() - 31, getGuiTop() + 86, 0, 0, 14, 14);
                        }
                    } else {
                        this.sub_button = false;
                        this.blit(matrix, getGuiLeft() - 31, getGuiTop() + 86, 28, 0, 14, 14);
                    }

                } else {
                    if (comSub < 15) {
                        this.add_button = true;
                        if (mouseX >= -31 && mouseX <= -18 && mouseY >= 86 && mouseY <= 99) {
                            this.blit(matrix, getGuiLeft() - 31, getGuiTop() + 86, 14, 14, 14, 14);
                        } else {
                            this.blit(matrix, getGuiLeft() - 31, getGuiTop() + 86, 0, 14, 14, 14);
                        }
                    } else {
                        this.add_button = false;
                        this.blit(matrix, getGuiLeft() - 31, getGuiTop() + 86, 28, 14, 14, 14);
                    }
                }
            }
        }
    }

    private void addInventoryButtons(PoseStack matrix, int mouseX, int mouseY) {
        if (!getMenu().showInventoryButtons()) {
            this.blit(matrix, getGuiLeft() - 20, getGuiTop() + 4, 0, 28, 23, 26);
        } else if (getMenu().showInventoryButtons()) {
            this.blit(matrix, getGuiLeft() - 56, getGuiTop() + 4, 0, 54, 59, 107);
            if (mouseX >= -47 && mouseX <= -34 && mouseY >= 12 && mouseY <= 25 || this.getMenu().getAutoInput()) {
                this.blit(matrix, getGuiLeft() - 47, getGuiTop() + 12, 0, 189, 14, 14);
            }
            if (mouseX >= -29 && mouseX <= -16 && mouseY >= 12 && mouseY <= 25 || this.getMenu().getAutoOutput()) {
                this.blit(matrix, getGuiLeft() - 29, getGuiTop() + 12, 14, 189, 14, 14);
            }
            this.blitIO(matrix);
        }
    }

    private void blitRedstone(PoseStack matrix) {
        boolean flag = isShiftKeyDown();
        if (flag) {
            this.blit(matrix, getGuiLeft() - 31, getGuiTop() + 70, 84, 189, 14, 14);
        }
        int setting = this.getMenu().getRedstoneMode();
        if (setting == 0) {
            this.blit(matrix, getGuiLeft() - 47, getGuiTop() + 70, 28, 189, 14, 14);
        } else if (setting == 1 && !flag) {
            this.blit(matrix, getGuiLeft() - 31, getGuiTop() + 70, 42, 189, 14, 14);
        } else if (setting == 2) {
            this.blit(matrix, getGuiLeft() - 31, getGuiTop() + 70, 98, 189, 14, 14);
        } else if (setting == 3) {
            this.blit(matrix, getGuiLeft() - 15, getGuiTop() + 70, 56, 189, 14, 14);
        } else if (setting == 4) {
            this.blit(matrix, getGuiLeft() - 47, getGuiTop() + 86, 70, 189, 14, 14);
        }
    }

    private void blitIO(PoseStack matrix) {
        int[] settings = new int[]{0, 0, 0, 0, 0, 0};
        int setting = this.getMenu().getSettingTop();
        if (setting == 1) {
            this.blit(matrix, getGuiLeft() - 32, getGuiTop() + 31, 0, 161, 10, 10);
        } else if (setting == 2) {
            this.blit(matrix, getGuiLeft() - 32, getGuiTop() + 31, 10, 161, 10, 10);
        } else if (setting == 3) {
            this.blit(matrix, getGuiLeft() - 32, getGuiTop() + 31, 20, 161, 10, 10);
        } else if (setting == 4) {
            this.blit(matrix, getGuiLeft() - 32, getGuiTop() + 31, 30, 161, 10, 10);
        }
        settings[1] = setting;

        setting = this.getMenu().getSettingBottom();
        if (setting == 1) {
            this.blit(matrix, getGuiLeft() - 32, getGuiTop() + 55, 0, 161, 10, 10);
        } else if (setting == 2) {
            this.blit(matrix, getGuiLeft() - 32, getGuiTop() + 55, 10, 161, 10, 10);
        } else if (setting == 3) {
            this.blit(matrix, getGuiLeft() - 32, getGuiTop() + 55, 20, 161, 10, 10);
        } else if (setting == 4) {
            this.blit(matrix, getGuiLeft() - 32, getGuiTop() + 55, 30, 161, 10, 10);
        }
        settings[0] = setting;
        setting = this.getMenu().getSettingFront();
        if (setting == 1) {
            this.blit(matrix, getGuiLeft() - 32, getGuiTop() + 43, 0, 161, 10, 10);
        } else if (setting == 2) {
            this.blit(matrix, getGuiLeft() - 32, getGuiTop() + 43, 10, 161, 10, 10);
        } else if (setting == 3) {
            this.blit(matrix, getGuiLeft() - 32, getGuiTop() + 43, 20, 161, 10, 10);
        } else if (setting == 4) {
            this.blit(matrix, getGuiLeft() - 32, getGuiTop() + 43, 30, 161, 10, 10);
        }
        settings[this.getMenu().getIndexFront()] = setting;
        setting = this.getMenu().getSettingBack();
        if (setting == 1) {
            this.blit(matrix, getGuiLeft() - 20, getGuiTop() + 55, 0, 161, 10, 10);
        } else if (setting == 2) {
            this.blit(matrix, getGuiLeft() - 20, getGuiTop() + 55, 10, 161, 10, 10);
        } else if (setting == 3) {
            this.blit(matrix, getGuiLeft() - 20, getGuiTop() + 55, 20, 161, 10, 10);
        } else if (setting == 4) {
            this.blit(matrix, getGuiLeft() - 20, getGuiTop() + 55, 30, 161, 10, 10);
        }
        settings[this.getMenu().getIndexBack()] = setting;
        setting = this.getMenu().getSettingLeft();
        if (setting == 1) {
            this.blit(matrix, getGuiLeft() - 44, getGuiTop() + 43, 0, 161, 10, 10);
        } else if (setting == 2) {
            this.blit(matrix, getGuiLeft() - 44, getGuiTop() + 43, 10, 161, 10, 10);
        } else if (setting == 3) {
            this.blit(matrix, getGuiLeft() - 44, getGuiTop() + 43, 20, 161, 10, 10);
        } else if (setting == 4) {
            this.blit(matrix, getGuiLeft() - 44, getGuiTop() + 43, 30, 161, 10, 10);
        }
        settings[this.getMenu().getIndexLeft()] = setting;
        setting = this.getMenu().getSettingRight();
        if (setting == 1) {
            this.blit(matrix, getGuiLeft() - 20, getGuiTop() + 43, 0, 161, 10, 10);
        } else if (setting == 2) {
            this.blit(matrix, getGuiLeft() - 20, getGuiTop() + 43, 10, 161, 10, 10);
        } else if (setting == 3) {
            this.blit(matrix, getGuiLeft() - 20, getGuiTop() + 43, 20, 161, 10, 10);
        } else if (setting == 4) {
            this.blit(matrix, getGuiLeft() - 20, getGuiTop() + 43, 30, 161, 10, 10);
        }
        settings[this.getMenu().getIndexRight()] = setting;
        boolean input = false;
        boolean output = false;
        boolean both = false;
        boolean fuel = false;
        for (int set : settings) {
            if (set == 1) {
                input = true;
            } else if (set == 2) {
                output = true;
            } else if (set == 3) {
                both = true;
            } else if (set == 4) {
                fuel = true;
            }
        }
        if (input || both) {
            this.blit(matrix, getGuiLeft() + 55, getGuiTop() + 16, 0, 171, 18, 18);
        }
        if (output || both) {
            this.blit(matrix, getGuiLeft() + 111, getGuiTop() + 30, 0, 203, 26, 26);
        }
        if (fuel) {
            this.blit(matrix, getGuiLeft() + 55, getGuiTop() + 52, 18, 171, 18, 18);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        double actualMouseX = mouseX - ((this.width - this.getXSize()) / 2);
        double actualMouseY = mouseY - ((this.height - this.getYSize()) / 2);
        this.mouseClickedRedstoneButtons(actualMouseX, actualMouseY);
        this.mouseClickedInventoryButtons(button, actualMouseX, actualMouseY);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public void mouseClickedInventoryButtons(int button, double mouseX, double mouseY) {
        boolean flag = button == GLFW.GLFW_MOUSE_BUTTON_2;
        if (!getMenu().showInventoryButtons()) {
            if (mouseX >= -20 && mouseX <= 0 && mouseY >= 4 && mouseY <= 26) {
                Messages.INSTANCE.sendToServer(new PacketShowSettingsButton(this.getMenu().getPos(), 1));
            }
        } else {
            if (mouseX >= -13 && mouseX <= 0 && mouseY >= 4 && mouseY <= 26) {
                Messages.INSTANCE.sendToServer(new PacketShowSettingsButton(this.getMenu().getPos(), 0));
            } else if (mouseX >= -47 && mouseX <= -34 && mouseY >= 12 && mouseY <= 25) {
                if (!this.getMenu().getAutoInput()) {
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), 6, 1));
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
                } else {
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), 6, 0));
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
                }

            } else if (mouseX >= -29 && mouseX <= -16 && mouseY >= 12 && mouseY <= 25) {
                if (!this.getMenu().getAutoOutput()) {
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), 7, 1));
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
                } else {
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), 7, 0));
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
                }
            } else if (mouseX >= -32 && mouseX <= -23 && mouseY >= 31 && mouseY <= 40) {
                if (flag) {
                    sendToServerInverted(this.getMenu().getSettingTop(), 1);
                } else {
                    sendToServer(this.getMenu().getSettingTop(), 1);
                }
            } else if (mouseX >= -32 && mouseX <= -23 && mouseY >= 55 && mouseY <= 64) {
                if (flag) {
                    sendToServerInverted(this.getMenu().getSettingBottom(), 0);
                } else {
                    sendToServer(this.getMenu().getSettingBottom(), 0);
                }
            } else if (mouseX >= -32 && mouseX <= -23 && mouseY >= 43 && mouseY <= 52) {
                if (isShiftKeyDown()) {
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), 0, 0));
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), 1, 0));
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), 2, 0));
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), 3, 0));
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), 4, 0));
                    Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), 5, 0));
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.8F, 0.3F));
                } else {
                    if (flag) {
                        sendToServerInverted(this.getMenu().getSettingFront(), this.getMenu().getIndexFront());
                    } else {
                        sendToServer(this.getMenu().getSettingFront(), this.getMenu().getIndexFront());
                    }
                }
            } else if (mouseX >= -20 && mouseX <= -11 && mouseY >= 55 && mouseY <= 64) {
                if (flag) {
                    sendToServerInverted(this.getMenu().getSettingBack(), this.getMenu().getIndexBack());
                } else {
                    sendToServer(this.getMenu().getSettingBack(), this.getMenu().getIndexBack());
                }
            } else if (mouseX >= -44 && mouseX <= -35 && mouseY >= 43 && mouseY <= 52) {
                if (flag) {
                    sendToServerInverted(this.getMenu().getSettingLeft(), this.getMenu().getIndexLeft());
                } else {
                    sendToServer(this.getMenu().getSettingLeft(), this.getMenu().getIndexLeft());
                }
            } else if (mouseX >= -20 && mouseX <= -11 && mouseY >= 43 && mouseY <= 52) {
                if (flag) {
                    sendToServerInverted(this.getMenu().getSettingRight(), this.getMenu().getIndexRight());
                } else {
                    sendToServer(this.getMenu().getSettingRight(), this.getMenu().getIndexRight());
                }
            }
        }
    }

    private void sendToServer(int setting, int index) {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
        if (setting <= 0) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), index, 1));
        } else if (setting == 1) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), index, 2));
        } else if (setting == 2) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), index, 3));
        } else if (setting == 3) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), index, 4));
        } else if (setting >= 4) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), index, 0));
        }
    }

    private void sendToServerInverted(int setting, int index) {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.3F, 0.3F));
        if (setting <= 0) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), index, 4));
        } else if (setting == 1) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), index, 0));
        } else if (setting == 2) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), index, 1));
        } else if (setting == 3) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), index, 2));
        } else if (setting >= 4) {
            Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), index, 3));
        }
    }

    public void mouseClickedRedstoneButtons(double mouseX, double mouseY) {
        if (mouseX >= -31 && mouseX <= -18 && mouseY >= 86 && mouseY <= 99) {
            if (this.sub_button && isShiftKeyDown()) {
                Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), 9, this.getMenu().getComSub() - 1));
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.3F, 0.3F));
            }
        }
        if (mouseX >= -31 && mouseX <= -18 && mouseY >= 86 && mouseY <= 99) {
            if (this.add_button && !isShiftKeyDown()) {
                Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), 9, this.getMenu().getComSub() + 1));
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
            }
        }
        if (mouseX >= -47 && mouseX <= -34 && mouseY >= 70 && mouseY <= 83) {
            if (this.getMenu().getRedstoneMode() != 0) {
                Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), 8, 0));
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
            }
        }

        if (mouseX >= -31 && mouseX <= -18 && mouseY >= 70 && mouseY <= 83) {
            if (this.getMenu().getRedstoneMode() != 1 && !isShiftKeyDown()) {
                Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), 8, 1));
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
            }
            if (this.getMenu().getRedstoneMode() != 2 && isShiftKeyDown()) {
                Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), 8, 2));
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
            }
        }

        if (mouseX >= -15 && mouseX <= -2 && mouseY >= 70 && mouseY <= 83) {
            if (this.getMenu().getRedstoneMode() != 3) {
                Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), 8, 3));
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
            }
        }

        if (mouseX >= -47 && mouseX <= -34 && mouseY >= 86 && mouseY <= 99) {
            if (this.getMenu().getRedstoneMode() != 4) {
                Messages.INSTANCE.sendToServer(new PacketSettingsButton(this.getMenu().getPos(), 8, 4));
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.6F, 0.3F));
            }
        }
    }

    public static boolean isShiftKeyDown() {
        return isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT) || isKeyDown(GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    public static boolean isKeyDown(int glfw) {
        InputConstants.Key key = InputConstants.Type.KEYSYM.getOrCreate(glfw);
        int keyCode = key.getValue();
        if (keyCode != InputConstants.UNKNOWN.getValue()) {
            long windowHandle = Minecraft.getInstance().getWindow().getWindow();
            try {
                if (key.getType() == InputConstants.Type.KEYSYM) {
                    return InputConstants.isKeyDown(windowHandle, keyCode);
                } /**else if (key.getType() == InputMappings.Type.MOUSE) {
                 return GLFW.glfwGetMouseButton(windowHandle, keyCode) == GLFW.GLFW_PRESS;
                 }**/
            } catch (Exception ignored) {
            }
        }
        return false;
    }
}