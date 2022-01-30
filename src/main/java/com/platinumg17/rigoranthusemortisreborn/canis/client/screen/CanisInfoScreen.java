package com.platinumg17.rigoranthusemortisreborn.canis.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.platinumg17.rigoranthusemortisreborn.RigoranthusEmortisReborn;
import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.CanisLevel;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumClothColor;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumMode;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature.EnumShadesColor;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;
import com.platinumg17.rigoranthusemortisreborn.canis.CanisSkills;
import com.platinumg17.rigoranthusemortisreborn.canis.client.screen.widget.*;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.CanisPacketHandler;
import com.platinumg17.rigoranthusemortisreborn.canis.common.canisnetwork.packet.data.*;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.CanisEntity;
import com.platinumg17.rigoranthusemortisreborn.canis.common.entity.accouterments.CanisAccouterments;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.Resources;
import com.platinumg17.rigoranthusemortisreborn.canis.common.util.REUtil;
import com.platinumg17.rigoranthusemortisreborn.config.Config;
import com.platinumg17.rigoranthusemortisreborn.magica.client.gui.NoShadowTextField;
import com.platinumg17.rigoranthusemortisreborn.magica.client.gui.buttons.CanisGuiImageButton;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author PlatinumG17
 */
@OnlyIn(Dist.CLIENT)
public class CanisInfoScreen extends CanisBaseBook {

    public final CanisEntity canis;
    public final Player player;
    private int currentPage = 0;
    private int maxPages = 1;
    private final List<Widget> skillWidgets;
    private BackButton leftButton;
    private ForwardButton rightButton;
    private final List<Skill> skillList;
    private BooleanButton obeyBtn;
    private BooleanButton attackPlayerBtn;
    private ModeButton modeButton;
    private float xMouse;
    private float yMouse;
    public NoShadowTextField nameTextField;
    public Button displayClothButton;
    public SmallBackButton prevClothColorBtn;
    public SmallForwardButton nextClothColorBtn;
    public SmallBackButton prevShadesColorBtn;
    public SmallForwardButton nextShadesColorBtn;
    public boolean clothButtonToggled;

    public CanisInfoScreen(CanisEntity canis, Player player) {
        this.canis = canis;
        this.player = player;
        this.skillList = RigoranthusEmortisRebornAPI.SKILLS
                .getValues()
                .stream()
                .sorted(Comparator.comparing((t) -> I18n.get(t.getTranslationKey())))
                .collect(Collectors.toList());
        this.skillWidgets = new ArrayList<>(16);
    }

    public static void open(CanisEntity canis) {
        Minecraft mc = Minecraft.getInstance();
        mc.setScreen(new CanisInfoScreen(canis, mc.player));
    }

    @Override
    public void init() {
        super.init();
        CanisInfoScreen.this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        nameTextField = new NoShadowTextField(this.font, bookLeft + 56, bookTop + 5, 200, 20,  new TranslatableComponent("canisgui.enter_name"));
        if(nameTextField.getValue().isEmpty())
            nameTextField.setSuggestion(new TranslatableComponent("canisgui.enter_name").getString());
        nameTextField.setResponder(text ->  { CanisPacketHandler.send(PacketDistributor.SERVER.noArg(), new CanisNameData(CanisInfoScreen.this.canis.getId(), text)); });
        nameTextField.setFocus(false);
        nameTextField.setBordered(false);
        nameTextField.setMaxLength(32);
        nameTextField.setTextColor(12694931);
        if (this.canis.hasCustomName()) { nameTextField.setValue(this.canis.getCustomName().getContents()); }
        this.addRenderableWidget(nameTextField);

        if (this.canis.isOwnedBy(this.player)) {
            obeyBtn = new BooleanButton(this.bookMiddle + 47, this.bookBottom - 48, new TextComponent(String.valueOf(this.canis.willObeyOthers())), (btn) -> {
                btn.setMessage(new TextComponent(String.valueOf(!this.canis.willObeyOthers())));
                CanisPacketHandler.send(PacketDistributor.SERVER.noArg(), new CanisObeyData(this.canis.getId(), !this.canis.willObeyOthers()));
            });
            this.addRenderableWidget(obeyBtn);

            attackPlayerBtn = new BooleanButton(this.bookMiddle + 47, this.bookBottom - 83, new TextComponent(String.valueOf(this.canis.canPlayersAttack())), button -> {
                button.setMessage(new TextComponent(String.valueOf(!this.canis.canPlayersAttack())));
                CanisPacketHandler.send(PacketDistributor.SERVER.noArg(), new FriendlyFireData(this.canis.getId(), !this.canis.canPlayersAttack()));
            });
            this.addRenderableWidget(attackPlayerBtn);
        }

        if (this.canis.getCanisLevel(CanisSkills.CAVALIER.get()) >= 1) {
            displayClothButton = new DisplayClothButton(this.bookLeft + 34, this.bookBottom - 105, new TextComponent(String.valueOf(this.canis.doDisplayCloth())), button -> {
                clothButtonToggled = !this.canis.doDisplayCloth();
                button.setMessage(new TextComponent(String.valueOf(!this.canis.doDisplayCloth())));
                CanisPacketHandler.send(PacketDistributor.SERVER.noArg(), new CanisSaddleClothData(this.canis.getId(), !this.canis.doDisplayCloth()));
            }) {
                @Override
                public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
                    List<Component> list = new ArrayList<>();
                    if (this.active) {
                        list.add(new TextComponent(String.valueOf(CanisInfoScreen.this.canis.doDisplayCloth())));
                    }
                    CanisInfoScreen.this.renderComponentTooltip(stack, list, mouseX, mouseY);
                }
            };
            this.addRenderableWidget(displayClothButton);
            nextClothColorBtn = new SmallForwardButton(this.bookLeft + 34, this.bookBottom - 90, new TranslatableComponent(this.canis.getClothColor().getUnlocalisedName()), button -> {
                EnumClothColor clothColor = CanisInfoScreen.this.canis.getClothColor().nextColor();
                button.setMessage(new TranslatableComponent(clothColor.getUnlocalisedName()));
                CanisPacketHandler.send(PacketDistributor.SERVER.noArg(), new ClothColorData(CanisInfoScreen.this.canis.getId(), clothColor));
            }) {
                @Override
                public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
                    String str = I18n.get(canis.getClothColor().getUnlocalisedName());
                    CanisInfoScreen.this.renderTooltip(stack, new TranslatableComponent(str).withStyle(ChatFormatting.ITALIC), mouseX, mouseY);
                }
            };
            this.addRenderableWidget(nextClothColorBtn);

            prevClothColorBtn = new SmallBackButton(this.bookLeft + 20, this.bookBottom - 90, new TranslatableComponent(this.canis.getClothColor().getUnlocalisedName()), button -> {
                EnumClothColor clothColor = CanisInfoScreen.this.canis.getClothColor().previousColor();
                button.setMessage(new TranslatableComponent(clothColor.getUnlocalisedName()));
                CanisPacketHandler.send(PacketDistributor.SERVER.noArg(), new ClothColorData(CanisInfoScreen.this.canis.getId(), clothColor));
            }) {
                @Override
                public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
                    String str = I18n.get(canis.getClothColor().getUnlocalisedName());
                    CanisInfoScreen.this.renderTooltip(stack, new TranslatableComponent(str).withStyle(ChatFormatting.ITALIC), mouseX, mouseY);
                }
            };
            this.addRenderableWidget(prevClothColorBtn);
        }

        if (this.canis.getAccoutrement(CanisAccouterments.SUNGLASSES.get()).isPresent()) {
            nextShadesColorBtn = new SmallForwardButton(this.bookLeft + 34, this.bookBottom - 64, new TranslatableComponent(this.canis.getShadesColor().getUnlocalisedName()), button -> {
                EnumShadesColor shadesColor = CanisInfoScreen.this.canis.getShadesColor().nextColor();
                button.setMessage(new TranslatableComponent(shadesColor.getUnlocalisedName()));
                CanisPacketHandler.send(PacketDistributor.SERVER.noArg(), new ShadesColorData(CanisInfoScreen.this.canis.getId(), shadesColor));
            }) {
                @Override
                public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
                    String str = I18n.get(canis.getShadesColor().getUnlocalisedName());
                    CanisInfoScreen.this.renderTooltip(stack, new TranslatableComponent(str).withStyle(ChatFormatting.ITALIC), mouseX, mouseY);
                }
            };
            this.addRenderableWidget(nextShadesColorBtn);

            prevShadesColorBtn = new SmallBackButton(this.bookLeft + 20, this.bookBottom - 64, new TranslatableComponent(this.canis.getShadesColor().getUnlocalisedName()), button -> {
                EnumShadesColor shadesColor = CanisInfoScreen.this.canis.getShadesColor().previousColor();
                button.setMessage(new TranslatableComponent(shadesColor.getUnlocalisedName()));
                CanisPacketHandler.send(PacketDistributor.SERVER.noArg(), new ShadesColorData(CanisInfoScreen.this.canis.getId(), shadesColor));
            }) {
                @Override
                public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
                    String str = I18n.get(canis.getShadesColor().getUnlocalisedName());
                    CanisInfoScreen.this.renderTooltip(stack, new TranslatableComponent(str).withStyle(ChatFormatting.ITALIC), mouseX, mouseY);
                }
            };
            this.addRenderableWidget(prevShadesColorBtn);
        }

        modeButton = new ModeButton(this.bookLeft + 44, this.bookBottom - 48, new TranslatableComponent(this.canis.getMode().getUnlocalisedName()), button -> {
            EnumMode mode = CanisInfoScreen.this.canis.getMode().nextMode();
            if (mode == EnumMode.WANDERING && !CanisInfoScreen.this.canis.getBowlPos().isPresent()) {
                button.setMessage(new TranslatableComponent(mode.getUnlocalisedName()).withStyle(ChatFormatting.RED));
            } else { button.setMessage(new TranslatableComponent(mode.getUnlocalisedName())); }
            CanisPacketHandler.send(PacketDistributor.SERVER.noArg(), new CanisModeData(CanisInfoScreen.this.canis.getId(), mode));
        }) {
            @Override
            public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
                List<Component> list = new ArrayList<>();
                String str = I18n.get(canis.getMode().getUnlocalisedInfo());
                list.addAll(ScreenUtil.splitInto(str, 150, CanisInfoScreen.this.font));
                if (CanisInfoScreen.this.canis.getMode() == EnumMode.WANDERING) {
                    if (CanisInfoScreen.this.canis.getBowlPos().isPresent()) {
                        double distance = CanisInfoScreen.this.canis.blockPosition().distSqr(CanisInfoScreen.this.canis.getBowlPos().get());
                        if (distance > 256D) { list.add(new TranslatableComponent("canis.mode.docile.distance", (int) Math.sqrt(distance)).withStyle(ChatFormatting.RED)); }
                        else { list.add(new TranslatableComponent("canis.mode.docile.bowl", (int) Math.sqrt(distance)).withStyle(ChatFormatting.GREEN)); }
                    } else { list.add(new TranslatableComponent("canis.mode.docile.nobowl").withStyle(ChatFormatting.RED)); }
                }
                CanisInfoScreen.this.renderComponentTooltip(stack, list, mouseX, mouseY);
            }
        };
        this.addRenderableWidget(modeButton);
        // Skill level-up buttons
        int size = RigoranthusEmortisRebornAPI.SKILLS.getKeys().size();
        int perPage = Math.max(Mth.floor((this.height - 10) / (double) 20) - 2, 1);
        this.currentPage = 0;
        this.recalculatePage(perPage);
        if (perPage < size) {
            this.leftButton = new BackButton(bookLeft - 48, perPage * 20 + 10, Component.nullToEmpty(""), (btn) -> {
                this.currentPage = Math.max(0, this.currentPage - 1);
                btn.active = this.currentPage > 0;
                this.rightButton.active = true;
                this.recalculatePage(perPage);
            }) { @Override public void renderToolTip(PoseStack stack, int mouseX, int mouseY) { CanisInfoScreen.this.renderTooltip(stack, new TranslatableComponent("canisgui.prevpage").withStyle(ChatFormatting.ITALIC), mouseX, mouseY); }};
            this.leftButton.active = false;
            this.rightButton = new ForwardButton(bookLeft - 25, perPage * 20 + 10, Component.nullToEmpty(""), (btn) -> {
                this.currentPage = Math.min(this.maxPages - 1, this.currentPage + 1);
                btn.active = this.currentPage < this.maxPages - 1;
                this.leftButton.active = true;
                this.recalculatePage(perPage);
                this.rightButton.visible = this.currentPage < this.maxPages;
            }) { @Override public void renderToolTip(PoseStack stack, int mouseX, int mouseY) { CanisInfoScreen.this.renderTooltip(stack, new TranslatableComponent("canisgui.nextpage").withStyle(ChatFormatting.ITALIC), mouseX, mouseY); }};
            this.addRenderableWidget(this.leftButton);
            this.addRenderableWidget(this.rightButton);
        }
        this.addRenderableWidget(new CanisGuiImageButton(bookRight - 68, bookBottom - 7, 0,0,41, 12, 41, 12, "textures/gui/clear_icon.png", (e) -> {Minecraft.getInstance().setScreen(null);}));
    }

    /**
    * @author ProPerciliv
    * */
    private void recalculatePage(int perPage) {
//        GlStateManager._pushMatrix();
//        if(scaleFactor != 1) {
//            GlStateManager._scalef(scaleFactor, scaleFactor, scaleFactor);
//            xMouse /= scaleFactor;
//            yMouse /= scaleFactor;
//        }
        this.skillWidgets.forEach(this::removeWidget);
        this.skillWidgets.clear();
        this.maxPages = Mth.ceil(this.skillList.size() / (double) perPage);
        for (int i = 0; i < perPage; ++i) {
            int index = this.currentPage * perPage + i;
            if (index >= this.skillList.size()) break;
            Skill skill = this.skillList.get(index);
            Button skillButton = new SkillButton(this.bookLeft - 135, 10 + i * 20, Component.nullToEmpty(""), skill, (btn) -> {
                int level = CanisInfoScreen.this.canis.getCanisLevel(skill);
                if (level < skill.getMaxLevel() && CanisInfoScreen.this.canis.canSpendPoints(skill.getLevelCost(level + 1))) {
                    CanisPacketHandler.send(PacketDistributor.SERVER.noArg(), new CanisSkillData(CanisInfoScreen.this.canis.getId(), skill));
                }}) {
                @Override
                public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
                    List<Component> list = new ArrayList<>();
                    list.add(new TranslatableComponent(skill.getTranslationKey()).withStyle(ChatFormatting.GREEN));
                    if (this.active) {
                        list.add(new TextComponent("Level: " + CanisInfoScreen.this.canis.getCanisLevel(skill)));
                        list.add(new TextComponent("--------------------------------").withStyle(ChatFormatting.GRAY));
                        list.addAll(ScreenUtil.splitInto(I18n.get(skill.getInfoTranslationKey()), 200, CanisInfoScreen.this.font));
                    } else { list.add(new TextComponent("Skill disabled").withStyle(ChatFormatting.RED)); }
                    CanisInfoScreen.this.renderComponentTooltip(stack, list, mouseX, mouseY);
                }
            };
            skillButton.active = Config.SKILL.getFlag(skill);
            this.skillWidgets.add(skillButton);
            this.addRenderableWidget(skillButton);
        }
//        GlStateManager._popMatrix();
    }

    @OnlyIn(Dist.CLIENT)
    protected void renderBg(PoseStack matrixStack, float partialTicks, int xMouse, int yMouse) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        InventoryScreen.renderEntityInInventory(bookLeft + 76, bookTop + 127, 17, (float)(bookLeft + 30) - this.xMouse, (float)(bookTop + 175 - 150) - this.yMouse, this.canis);
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        this.xMouse = (float)mouseX;
        this.yMouse = (float)mouseY;
//        String health = REUtil.format1DP(this.canis.getHealth());//        String healthMax = REUtil.format1DP(this.canis.getMaxHealth());//        String ageValue = REUtil.format2DP(this.canis.getAge());//        String ageRel = I18n.get(this.canis.isBaby() ? "canisgui.age.baby" : "canisgui.age.adult");//        String ageString = ageValue + " " + ageRel;//        this.font.draw(stack, I18n.get("canisgui.levelhomini") + " " + this.canis.getCanisLevel().getLevel(CanisLevel.Type.HOMINI), topX, topY + 75, 0xFF10F9);
        Component modeButtonData = this.modeButton.getMessage().copy();
        Component attackBoolean = this.attackPlayerBtn.getMessage().copy();
        Component obeyBoolean = this.obeyBtn.getMessage().copy();

        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderBg(stack, partialTicks, mouseX, mouseY);
        drawCenteredString(stack, this.font, modeButtonData, modeButton.x + 32, modeButton.y + 6, 43520);
        drawCenteredString(stack, this.font, attackBoolean, attackPlayerBtn.x + 20, attackPlayerBtn.y + 6,  43520);
        drawCenteredString(stack, this.font, obeyBoolean, obeyBtn.x + 20, obeyBtn.y + 6,  43520);
        this.renderables.forEach(widget -> {
            if (widget instanceof SkillButton) {
                SkillButton sklBTN = (SkillButton)widget;
                if (CanisInfoScreen.this.canis.getSkill(sklBTN.skill).isPresent()) {
                    if (CanisInfoScreen.this.canis.getSkill(sklBTN.skill).get().level() >= 5) {
                        this.font.draw(stack, I18n.get(sklBTN.skill.getTranslationKey()), sklBTN.x + 28, sklBTN.y + 6, 11184810);
                    } else { this.font.draw(stack, I18n.get(sklBTN.skill.getTranslationKey()), sklBTN.x + 28, sklBTN.y + 6, 11141290); }
                } else { this.font.draw(stack, I18n.get(sklBTN.skill.getTranslationKey()), sklBTN.x + 28, sklBTN.y + 6, 11141290); }
            }
        });
        nameTextField.setSuggestion(nameTextField.getValue().isEmpty() ? new TranslatableComponent("canisgui.enter_name").getString() : "");
        for (Widget widget : this.renderables) {
            if (widget instanceof AbstractWidget w && w.isHoveredOrFocused()) {
                w.renderToolTip(stack, mouseX, mouseY);
                break;
            }
        }
    }
    @Override public void removed() { super.removed();this.minecraft.keyboardHandler.setSendRepeatsToGui(false); }
    @Override public boolean isPauseScreen() { return false; }
    protected <T extends Widget> T removeWidget(T widgetIn) {
        renderables.remove(widgetIn);
        children().remove(widgetIn);
        return widgetIn;
    }

    @Override
    public void drawBackgroundElements(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.drawBackgroundElements(stack, mouseX, mouseY, partialTicks);
        String tamedString = "";
        if (this.canis.isTame()) {
            if (this.canis.isOwnedBy(this.player)) { tamedString = I18n.get("canisgui.owner.you"); }
            else if (this.canis.getOwnersName().isPresent()) { tamedString = this.canis.getOwnersName().get().getString(); }
        }
        String ownersName = "";
        if (this.canis.isTame()) {
            if (this.canis.getOwnersName().isPresent()) { ownersName = this.canis.getOwnersName().get().getString(); }
        }
        String speedValue = REUtil.format2DP(this.canis.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
        String armorValue = REUtil.format2DP(this.canis.getAttribute(Attributes.ARMOR).getValue());
        drawFromTexture(RigoranthusEmortisReborn.rl("textures/gui/create_paper.png"), 216, 181, 0, 0, 56, 15,56,15, stack);

        this.font.draw(stack, I18n.get("canisgui.speed") + "     " + speedValue, 177, 40, 0xFFFFFF);
        this.font.draw(stack, I18n.get("canisgui.owner") + "     " + tamedString, 177, 52, 0xFFFFFF);
        this.font.draw(stack, I18n.get("canisgui.armor") + "     " + armorValue, 177, 64, 0xFFFFFF);

        if (Config.CANIS_GENDER.get()) { this.font.draw(stack, I18n.get("canisgui.gender") + I18n.get(this.canis.getGender().getUnlocalisedName()), 177, 76, 0xFFFFFF);}
        minecraft.font.draw(stack, I18n.get("canisgui.friendlyfire"), 181, 97, 0x808080);
        if (this.canis.isOwnedBy(this.player)) { minecraft.font.draw(stack, I18n.get("canisgui.obeyothers"), 182, 132, 0x808080); }

        this.font.draw(stack, ChatFormatting.RED + I18n.get("canisgui.level") + ChatFormatting.ITALIC + ChatFormatting.DARK_PURPLE + this.canis.getCanisLevel().getLevel(CanisLevel.Type.CHORDATA), 21, 58, 0xFF10F9);
        //        this.font.draw(stack, I18n.get("canisgui.levelhomini") + " " + this.canis.getCanisLevel().getLevel(CanisLevel.Type.HOMINI), topX, topY + 75, 0xFF10F9);
        if (this.canis.getAccoutrement(CanisAccouterments.GOLDEN_COLLAR.get()).isPresent()) { minecraft.font.draw(stack, I18n.get("canisgui.unlimited"), 21, 68, 0x808080);
        } else if (this.canis.getSpendablePoints() >= 5) { minecraft.font.draw(stack, I18n.get("canisgui.pointsleft.high") + this.canis.getSpendablePoints(), 21, 68, 0x808080);
        } else if (this.canis.getSpendablePoints() >= 1) { minecraft.font.draw(stack, I18n.get("canisgui.pointsleft.medium") + this.canis.getSpendablePoints(), 21, 68, 0x808080);
        } else { minecraft.font.draw(stack, I18n.get("canisgui.pointsleft.low") + this.canis.getSpendablePoints(), 21, 68, 0x808080); }
        minecraft.font.draw(stack, ownersName + I18n.get("canisgui.book_title1"), 21, 32, 0x808080);
        minecraft.font.draw(stack, I18n.get("canisgui.book_title2"), 21, 42, 0x808080);
        minecraft.font.draw(stack, new TranslatableComponent("canisgui.close"), 237, 186, -8355712);
    }

    private class SkillButton extends Button {
        protected Skill skill;
        private SkillButton(int x, int y, Component buttonText, Skill skill, Consumer<SkillButton> onPress) {
            super(x, y, 130, 19, buttonText, button -> onPress.accept((SkillButton) button));
            this.skill = skill;
        }
        @Override
        public void renderButton(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
            Minecraft mc = Minecraft.getInstance();
//        Font font = mc.font;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.setShaderTexture(0, Resources.SKILL_BUTTON);
            int i = this.getYImage(this.isHoveredOrFocused());
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            if (CanisInfoScreen.this.canis.getSkill(this.skill).isPresent()) {
                if (CanisInfoScreen.this.canis.getSkill(this.skill).get().level() >= 5) {
                    this.blit(stack, this.x, this.y, 0, 0, this.width, this.height);
                } else { this.blit(stack, this.x, this.y, 0, 38 + (i * 19), this.width, this.height); }
            } else { this.blit(stack, this.x, this.y, 0, i * 19, this.width, this.height); }
            this.renderBg(stack, mc, mouseX, mouseY);
//        int j = getFGColor();
//        this.drawCenteredString(stack, font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
        }
    }

    public class DisplayClothButton extends Button {
        public DisplayClothButton(int x, int y, Component text, OnPress onPress) {
            super(x, y, 14, 14, text, onPress);
        }
        @Override
        public void renderButton(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
            Minecraft mc = Minecraft.getInstance();
//        Font font = mc.font;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.setShaderTexture(0, Resources.SMALL_WIDGETS);
            int i = this.getYImage(this.isHoveredOrFocused());
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            if (CanisInfoScreen.this.clothButtonToggled) { this.blit(stack, this.x, this.y, 48, 0, this.width, this.height); }
            else { this.blit(stack, this.x, this.y, 48, i * 14, this.width, this.height); }
            this.renderBg(stack, mc, mouseX, mouseY);
//        int j = getFGColor();
//        this.drawCenteredString(stack, font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
        }
    }
    public static TranslatableComponent langDesc = new TranslatableComponent("rigoranthusemortisreborn.canis_desc");
}