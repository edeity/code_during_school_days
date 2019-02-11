#_*_coding:utf-8_*_
'''
Created on 2013年9月25日

@author: javer
'''
import pygame
import pygame._view
from sys import exit
import programManager

INTERFACELENGHT = 800
INTERFACEHEIGHT = 500
flag = 0  #pygame.FULLSCREEN      pygame.NOFRAME 无框    0
dir_name = 'image/interface/'
back_t_image_filename = dir_name + 'back_t.png'
back_f_image_filename = dir_name + 'back_f.png'
back_t = pygame.image.load(back_t_image_filename)
back_f = pygame.image.load(back_f_image_filename)
y_t_image_filename = dir_name + 'Y_t.png'
y_f_image_filename = dir_name + 'Y_f.png'
n_t_image_filename = dir_name + 'N_t.png'
n_f_image_filename = dir_name + 'N_f.png'
on_t_image_filename = dir_name + 'on_t.png'
on_f_image_filename = dir_name + 'on_f.png'
off_t_image_filename = dir_name + 'off_t.png'
off_f_image_filename = dir_name + 'off_f.png'
y_t = pygame.image.load(y_t_image_filename)
y_f = pygame.image.load(y_f_image_filename)
n_t = pygame.image.load(n_t_image_filename)
n_f = pygame.image.load(n_f_image_filename)
on_t = pygame.image.load(on_t_image_filename)
on_f = pygame.image.load(on_f_image_filename)
off_t = pygame.image.load(off_t_image_filename)
off_f = pygame.image.load(off_f_image_filename)
#第一图需要的图片
first_background_image_filename = dir_name + 'blue.jpg'
first_background_image = pygame.image.load(first_background_image_filename)
start_t_image_filename = dir_name + 'start.png'
start_f_image_filename = dir_name + 'start_f.png'
settings_t_image_filename = dir_name + 'settings.png'
settings_f_image_filename = dir_name + 'settings_f.png'
help_t_image_filename = dir_name + 'help.png'
help_f_image_filename = dir_name + 'help_f.png'
about_t_image_filename = dir_name + 'about.png'
about_f_image_filename = dir_name + 'about_f.png'
start_t = pygame.image.load(start_t_image_filename)
start_f = pygame.image.load(start_f_image_filename)
settings_t = pygame.image.load(settings_t_image_filename)
settings_f = pygame.image.load(settings_f_image_filename)
help_t= pygame.image.load(help_t_image_filename)
help_f = pygame.image.load(help_f_image_filename)
about_t = pygame.image.load(about_t_image_filename)
about_f = pygame.image.load(about_f_image_filename)
#setting里面的图片
sound_t_image_filename = dir_name + 'sound_t.png'
sound_f_image_filename = dir_name + 'sound_f.png'
sound_t = pygame.image.load(sound_t_image_filename)
sound_f = pygame.image.load(sound_f_image_filename)
#help里面的图片
help_book01 = pygame.image.load(dir_name + 'help_book01.jpg')
help_book02 = pygame.image.load(dir_name + 'help_book02.jpg')
help_book03 = pygame.image.load(dir_name + 'help_book03.jpg')
next_t = pygame.image.load(dir_name + 'next_t.png')
next_f = pygame.image.load(dir_name + 'next_f.png')
#第二图需要的图片
#按钮
start_background_image_filename = dir_name + 'start_background.jpg'
start_background_image = pygame.image.load(start_background_image_filename)
simple_t_image_filename = dir_name + 'simple_t.png'
simple_f_imaeg_filename = dir_name + 'simple_f.png'
survive_t_image_filename = dir_name + 'survive_t.png'
survive_f_image_filename = dir_name + 'survive_f.png'
race_t_image_filename = dir_name + 'race_t.png'
race_f_image_filename = dir_name + 'race_f.png'
person_t_image_filename = dir_name + 'person_t.png'
person_f_image_filename = dir_name + 'person_f.png'
simple_t = pygame.image.load(simple_t_image_filename)
simple_f = pygame.image.load(simple_f_imaeg_filename)
survive_t = pygame.image.load(survive_t_image_filename)
survive_f = pygame.image.load(survive_f_image_filename)
race_t = pygame.image.load(race_t_image_filename)
race_f = pygame.image.load(race_f_image_filename)
person_t = pygame.image.load(person_t_image_filename)
person_f =  pygame.image.load(person_f_image_filename)
#大图
bg_simple_t_image_filename = dir_name + 'bg_simple_t.png'
bg_simple_f_image_filename = dir_name + 'bg_simple_f.png'
bg_survive_t_image_filename = dir_name + 'bg_survive_t.png'
bg_survive_f_image_filename = dir_name + 'bg_survive_f.png'
bg_race_t_image_filename = dir_name + 'bg_race_t.png'
bg_race_f_image_filename = dir_name + 'bg_race_f.png'
bg_person_t_image_filename = dir_name + 'bg_person_t.png'
bg_person_f_image_filename = dir_name + 'bg_person_f.png'

bgsimple_t = pygame.image.load(bg_simple_t_image_filename)
bgsimple_f = pygame.image.load(bg_simple_f_image_filename)
bgsurvive_t = pygame.image.load(bg_survive_t_image_filename)
bgsurvive_f = pygame.image.load(bg_survive_f_image_filename)
bgrace_t = pygame.image.load(bg_race_t_image_filename)
bgrace_f = pygame.image.load(bg_race_f_image_filename)
bgperson_t = pygame.image.load(bg_person_t_image_filename)
bgperson_f = pygame.image.load(bg_person_f_image_filename)
#关卡选择
magic_forest_t_image_filename = dir_name + 'magic_forest_t.png' 
magic_forest_f_image_filename = dir_name + 'magic_forest_f.png' 
deep_sea_t_image_filename = dir_name + 'deep_sea_t.png' 
deep_sea_f_image_filename = dir_name + 'deep_sea_f.png' 
seventeenth_street_t_image_filename = dir_name + 'seventeenth_street_t.png'
seventeenth_street_f_image_filename = dir_name + 'seventeenth_street_f.png'
stone_city_t_image_filename = dir_name + 'stone_city_t.png' 
stone_city_f_image_filename = dir_name + 'stone_city_f.png' 
magic_forest_t= pygame.image.load(magic_forest_t_image_filename)
magic_forest_f= pygame.image.load(magic_forest_f_image_filename)
deep_sea_t= pygame.image.load(deep_sea_t_image_filename)
deep_sea_f_= pygame.image.load(deep_sea_f_image_filename)
seventeen_street_t= pygame.image.load(seventeenth_street_t_image_filename)
seventeen_street_f= pygame.image.load(seventeenth_street_f_image_filename)
stone_city_t= pygame.image.load(stone_city_t_image_filename)
stone_city_f= pygame.image.load(stone_city_f_image_filename)
#下面是按钮
one_t_image_filename = dir_name + 'one_t.png'
one_f_image_filename = dir_name +'one_f.png' 
two_t_image_filename = dir_name + 'two_t.png' 
two_f_image_filename = dir_name + 'two_f.png' 
three_t_image_filename = dir_name + 'three_t.png' 
three_f_image_filename = dir_name + 'three_f.png' 
four_t_image_filename = dir_name + 'four_t.png' 
four_f_image_filename = dir_name + 'four_f.png' 
five_t_image_filename = dir_name + 'five_t.png' 
five_f_image_filename = dir_name + 'five_f.png' 
six_t_image_filename = dir_name + 'six_t.png' 
six_f_image_filename = dir_name + 'six_f.png' 
seven_t_image_filename = dir_name + 'seven_t.png' 
seven_f_image_filename = dir_name + 'seven_f.png' 
eight_t_image_filename = dir_name + 'eight_t.png' 
eight_f_image_filename = dir_name + 'eight_f.png' 
nine_t_image_filename = dir_name + 'nine_t.png' 
nine_f_image_filename = dir_name + 'nine_f.png' 
one_t = pygame.image.load(one_t_image_filename)
one_f = pygame.image.load(one_f_image_filename)
two_t= pygame.image.load(two_t_image_filename)
two_f= pygame.image.load(two_f_image_filename)
three_t= pygame.image.load(three_t_image_filename)
three_f = pygame.image.load(three_f_image_filename)
four_t = pygame.image.load(four_t_image_filename)
four_f = pygame.image.load(four_f_image_filename)
five_t= pygame.image.load(five_t_image_filename)
five_f = pygame.image.load(five_f_image_filename)
six_t= pygame.image.load(six_t_image_filename)
six_f = pygame.image.load(six_f_image_filename)
seven_t = pygame.image.load(seven_t_image_filename)
seven_f= pygame.image.load(seven_f_image_filename)
eight_t = pygame.image.load(eight_t_image_filename)
eight_f = pygame.image.load(eight_f_image_filename)
nine_t = pygame.image.load(nine_t_image_filename)
nine_f = pygame.image.load(nine_f_image_filename)
forest_t_image_filename = dir_name + 'forest_t.png' 
forest_f_image_filename = dir_name + 'forest_f.png' 
sea_t_image_filename = dir_name + 'sea_t.png' 
sea_f_image_filename = dir_name + 'sea_f.png' 
street_t_image_filename = dir_name + 'street_t.png'
street_f_image_filename = dir_name + 'street_f.png'
stone_t_image_filename = dir_name + 'stone_t.png' 
stone_f_image_filename = dir_name + 'stone_f.png' 
forest_t= pygame.image.load(forest_t_image_filename)
forest_f= pygame.image.load(forest_f_image_filename)
sea_t= pygame.image.load(sea_t_image_filename)
sea_f= pygame.image.load(sea_f_image_filename)
street_t= pygame.image.load(street_t_image_filename)
street_f= pygame.image.load(street_f_image_filename)
stone_t= pygame.image.load(stone_t_image_filename)
stone_f= pygame.image.load(stone_f_image_filename)
#生存模式的地图:
tree_t_image_filename = dir_name + 'tree_t.png' 
tree_f_image_filename = dir_name + 'tree_f.png' 
octopus_t_image_filename = dir_name + 'octopus_t.png' 
octopus_f_image_filename = dir_name + 'octopus_f.png' 
mummy_t_image_filename = dir_name + 'mummy_t.png'
mummy_f_image_filename = dir_name + 'mummy_f.png'
rockman_t_image_filename = dir_name + 'rockman_t.png' 
rockman_f_image_filename = dir_name + 'rockman_f.png' 
tree_t= pygame.image.load(tree_t_image_filename)
tree_f= pygame.image.load(tree_f_image_filename)
octopus_t= pygame.image.load(octopus_t_image_filename)
octopus_f= pygame.image.load(octopus_f_image_filename)
mummy_t= pygame.image.load(mummy_t_image_filename)
mummy_f= pygame.image.load(mummy_f_image_filename)
rockman_t = pygame.image.load(rockman_t_image_filename)
rockman_f = pygame.image.load(rockman_f_image_filename)
#背景
chapter_bg = pygame.image.load(dir_name + 'chapter_bg.jpg')
win_bg = pygame.image.load(dir_name + 'win.jpg')
gameover_bg = pygame.image.load(dir_name + 'gameover.jpg')
'''自定义按钮'''
class Button:
    def __init__(self, interface, function, image_t, image_f, pos):#起点的X, Y坐标, images包含两张图片, 0代表没有聚焦. 1代表聚焦, pos是一个元组
        self.interface = interface
        self.function = function
        self.pos_x = pos[0]
        self.pos_y = pos[1]
        self.image_t = image_t#代表聚焦的时候的图片
        self.image_f = image_f
        self.is_focus = False
        self.rect = pygame.Rect((self.pos_x, self.pos_y), (self.image_t.get_width(), self.image_t.get_height()))
    def focus(self):#鼠标聚焦
        if self.rect.collidepoint(self.interface.mouse_pos):
            self.is_focus = True
            self.interface.screen.blit(self.image_t, (self.pos_x, self.pos_y))
        else:
            self.is_focus = False
            self.interface.screen.blit(self.image_f, (self.pos_x, self.pos_y))
    def click(self):#0代表不起任何作用
        self.is_focus = False#修复点击未能修改属性的BUG
        if self.function == 1:#start    
            self.interface.other_buttons = []
            self.interface.background_image = start_background_image
            self.interface.buttons = self.interface.start_btns
        elif self.function == 2:#settings
            self.interface.other_buttons = [self.interface.sound_btn, self.interface.on_btn, self.interface.off_btn]
        elif self.function == 3:#help
            self.interface.other_buttons = []
            self.interface.background_image = help_book01
            self.interface.buttons = self.interface.help_btns
        elif self.function == 4:#about
            print 'about'
        elif self.function == 5:#simple
            self.interface.other_buttons = [self.interface.bgsimple_btn]
        elif self.function == 6:#survive
            self.interface.other_buttons = [self.interface.bgsurvive_btn]
        elif self.function == 7:#race
            self.interface.other_buttons = [self.interface.bgrace_btn]
        elif self.function == 8:#person
            self.interface.other_buttons = [self.interface.bgperson_btn]
        elif self.function == 9:#back_first
            self.interface.background_image = first_background_image
            self.interface.other_buttons = []
            self.interface.buttons = self.interface.first_btns
        elif self.function == 10:#simple_start
            programManager.model = 0
            self.interface.background_image = chapter_bg
            self.interface.buttons = self.interface.simple_btns
            self.interface.other_buttons = [self.interface.forset_btn]
        elif self.function == 11:#survive_start
            programManager.model = 1
            self.interface.background_image = chapter_bg
            self.interface.buttons = self.interface.survive_btns
            self.interface.other_buttons = []
        elif self.function == 12:#race_start
            programManager.model = 2
            self.interface.background_image = chapter_bg
            self.interface.buttons = self.interface.race_btns
            self.interface.other_buttons = []
        elif self.function == 13:#person_start
            programManager.model = 3
            proManager = programManager.ProgramManager()
            proManager.run()
        elif self.function == 14:#one
            programManager.level = 1
            proManager = programManager.ProgramManager()
            proManager.run()
        elif self.function == 15:#two
            programManager.level = 2
            proManager = programManager.ProgramManager()
            proManager.run()
        elif self.function == 16:#three
            programManager.level = 3
            proManager = programManager.ProgramManager()
            proManager.run()
        elif self.function == 17:
            programManager.level = 4
            proManager = programManager.ProgramManager()
            proManager.run()
        elif self.function == 18:#five
            programManager.level = 5
            proManager = programManager.ProgramManager()
            proManager.run()
        elif self.function == 19:#six
            programManager.level = 6
            proManager = programManager.ProgramManager()
            proManager.run()
        elif self.function == 20:#seven
            programManager.level = 7
            proManager = programManager.ProgramManager()
            proManager.run()
        elif self.function == 21:#eight
            programManager.level = 8
            proManager = programManager.ProgramManager()
            proManager.run()
        elif self.function == 22:#nine
            programManager.level = 9
            proManager = programManager.ProgramManager()
            proManager.run()
        elif self.function == 23:#forest
            self.interface.other_buttons = [self.interface.forset_btn]
            programManager.chapter = 1
        elif self.function == 24:#sea
            self.interface.other_buttons = [self.interface.sea_btn]
            programManager.chapter = 2
        elif self.function == 25:#street
            self.interface.other_buttons = [self.interface.street_btn]
            programManager.chapter = 3
        elif self.function == 26:#stone
            self.interface.other_buttons = [self.interface.stone_btn]
            programManager.chapter = 4
        elif self.function == 27:#back to start_interface
            self.interface.background_image = start_background_image
            self.interface.buttons = self.interface.start_btns
            self.interface.other_buttons = []
        elif self.function == 28:#on
            programManager.volumn = 1
        elif self.function == 29:#off
            programManager.volumn = 0
        elif self.function == 30:#survive_1:tree
            self.interface.other_buttons = [self.interface.tree_btn]
        elif self.function == 31:#survive_2:octopus
            self.interface.other_buttons = [self.interface.octopus_btn]
        elif self.function == 32:#survive_3:mummy
            self.interface.other_buttons = [self.interface.mummy_btn]
        elif self.function == 33:#survive_4:rockman
            self.interface.other_buttons = [self.interface.rockman_btn]
        elif self.function == 34:#tree_survive
            programManager.chapter = 1
            proManager = programManager.ProgramManager()
            proManager.run()
        elif self.function == 35:#octopus_survive
            programManager.chapter = 2
            proManager = programManager.ProgramManager()
            proManager.run()
        elif self.function == 36:#mummy_survive
            programManager.chapter = 3
            proManager = programManager.ProgramManager()
            proManager.run()
        elif self.function == 37:#rockman_survive
            programManager.chapter = 4
            proManager = programManager.ProgramManager()
            proManager.run()
        elif self.function == 38:#back_first
            self.interface.background_image = first_background_image
            self.interface.buttons = self.interface.first_btns
        elif self.function == 39:#help_next
            if self.interface.background_image == help_book01:
                self.interface.background_image = help_book02
            elif self.interface.background_image == help_book02:
                self.interface.background_image = help_book03
            elif self.interface.background_image == help_book03:
                self.interface.background_image = help_book01
        elif self.function == 40:#end_back
            self.interface.choose_interface(self.interface.model)

'''画板'''
class Interface:
    def __init__(self):   
        pygame.init()
        #传递参数
        self.buttons = []#所包含的组
        self.other_buttons = []#容易变化的组
        #其他初始化
        self.model = 0
        self.mouse_pos = [0, 0]
        self.time = pygame.time.Clock()
        #开始界面
        self.background_image = first_background_image
        #游戏连接到界面
        programManager.InterfaceBridge(self)
        #点击开始的界面
        #第一组
        start_btn = Button(self, 1, start_t, start_f, (30, 300))
        settings_btn = Button(self, 2, settings_t, settings_f, (50, 350))
        help_btn = Button(self, 3, help_t, help_f, (20, 400))
        about_btn = Button(self, 4, about_t, about_f, (40, 450))
        self.first_btns = [start_btn, settings_btn, help_btn, about_btn]
        #可选按钮
        self.sound_btn = Button(self, 0, sound_t, sound_f, (240, 360))
        self.on_btn = Button(self, 28, on_t, on_f, (350, 360))
        self.off_btn = Button(self, 29, off_t, off_f, (420, 350))
        #第二组
        simple_btn = Button(self, 5, simple_t, simple_f, (30, 40))
        survive_btn = Button(self, 6, survive_t, survive_f, (200, 10))
        race_btn = Button(self, 7, race_t, race_f, (400, 20))
        person_btn = Button(self, 8, person_t, person_f, (600, 50))
        start_back_btn = Button(self, 9, back_t, back_f, (20, 420))
        self.start_btns = [simple_btn, survive_btn, race_btn, person_btn, start_back_btn]
        #可选的按钮
        self.bgsimple_btn = Button(self, 10, bgsimple_t, bgsimple_f, (200, 140))
        self.bgsurvive_btn = Button(self, 11, bgsurvive_t, bgsurvive_f, (230, 130))
        self.bgrace_btn = Button(self, 12, bgrace_t, bgrace_f, (260, 130))
        self.bgperson_btn = Button(self, 13, bgperson_t, bgperson_f, (290, 140))
        #self.start_chooses = [self.bgsimple_btn, self.bgsurvive_btn, self.bgrace_btn, self.bgperson_btn]
        #初始化
        #第三组,章节选择界面
        one_btn = Button(self, 14, one_t, one_f, (160, 30))
        two_btn = Button(self, 15, two_t, two_f, (350, 30))
        three_btn =Button(self, 16, three_t, three_f, (580, 20))
        four_btn = Button(self, 17, four_t, four_f, (170, 210))
        five_btn = Button(self, 18, five_t, five_f, (360, 200))
        six_btn = Button(self, 19, six_t, six_f, (640, 210))
        seven_btn = Button(self, 20, seven_t, seven_f, (160, 340))
        eight_btn = Button(self, 21, eight_t, eight_f, (360, 350))
        nine_btn = Button(self, 22, nine_t, nine_f, (610, 340))
        choose_forest = Button(self, 23, forest_t, forest_f, (270, 430))
        choose_sea = Button(self, 24, sea_t, sea_f, (380, 430))
        choose_street = Button(self, 25, street_t, street_f, (450, 430))
        choose_stone = Button(self, 26, stone_t, stone_f, (570, 430))
        simple_back_btn = Button(self, 27, back_t, back_f, (20, 420))
        survive_back_btn = Button(self, 27, back_t, back_f, (20, 420))
        race_back_btn = Button(self, 27, back_t, back_f, (20, 420))
        #person_back_btn = Button(self, 27, back_t, back_f, (20, 420))
        survive_one_btn = Button(self, 30, one_t, one_f, (60, 60))
        survive_two_btn = Button(self, 31, two_t, two_f, (60, 120))
        survive_three_btn = Button(self, 32, three_t, three_f, (60, 180))
        survive_four_btn = Button(self, 33, four_t, four_f, (60, 240))
        #生存可选按钮
        self.tree_btn = Button(self, 34, tree_t, tree_f, (250, 40))
        self.octopus_btn = Button(self, 35, octopus_t, octopus_f, (240, 60))
        self.mummy_btn = Button(self, 36, mummy_t, mummy_f, (250, 80))
        self.rockman_btn = Button(self, 37, rockman_t, rockman_f, (240, 100))
        #
        self.simple_btns = [one_btn, two_btn, three_btn, four_btn, five_btn, six_btn, seven_btn, eight_btn, nine_btn, choose_forest, choose_sea, choose_street, choose_stone, simple_back_btn]
        self.survive_btns = [survive_one_btn, survive_two_btn, survive_three_btn, survive_four_btn, survive_back_btn]
        self.race_btns = [one_btn, two_btn, three_btn, four_btn, five_btn, race_back_btn]
        self.person_btns = []
        #可选按钮:图片
        self.forset_btn = Button(self, 0, magic_forest_t, magic_forest_f, (180, 30))
        self.sea_btn = Button(self, 0, deep_sea_t, deep_sea_f_, (180, 30))
        self.street_btn = Button(self, 0, seventeen_street_t, seventeen_street_f, (180, 30))
        self.stone_btn = Button(self, 0, stone_city_t, stone_city_f, (180, 30))
        self.buttons = self.first_btns
        #help界面的按钮
        help_back_btn = Button(self, 38, back_t, back_f, (40, 430))
        help_next_btn = Button(self, 39, next_t, next_f, (630, 440))
        self.help_btns = [help_back_btn, help_next_btn]
        #显示结果的按钮
        end_back_btn = Button(self, 40, back_t, back_f, (20, 420))
        self.end_btns = [end_back_btn]
    def monitor(self):#监听器
        self.mouse_pos = pygame.mouse.get_pos()
        #关闭事件
        for event in pygame.event.get():
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    exit()
            if event.type == pygame.QUIT:
                exit()
            #是否点击鼠标
            if event.type == pygame.MOUSEBUTTONDOWN:
                if event.button == 1:
                    #是否焦点是True
                    for button in self.buttons:
                        if button.is_focus == True:
                            button.click() 
                    for other_btn in self.other_buttons:
                        if other_btn.is_focus == True:
                            other_btn.click()
    def return_interface(self, model, end_game_kind):
        self.model = model#保留游戏中的信息
        if end_game_kind == 0:
            self.background_image = gameover_bg
        elif end_game_kind == 1:
            self.background_image = win_bg
        self.buttons = self.end_btns
        self.other_buttons = []
        self.run_interface()
    def choose_interface(self, model):#离开游戏, 返回界面
        if model == 0:#model 就是模式
            self.background_image = chapter_bg
            self.buttons = self.simple_btns
            self.other_buttons = [self.forset_btn]
            self.run_interface()
        elif model == 1:
            self.background_image = chapter_bg
            self.buttons = self.survive_btns
            self.run_interface()
        elif model == 2:
            self.background_image = chapter_bg
            self.buttons = self.race_btns
            self.run_interface()
        elif model == 3:
            self.background_image = start_background_image
            self.buttons = self.start_btns
            self.run_interface()
    def run_interface(self):#启动项
        self.screen = pygame.display.set_mode((INTERFACELENGHT , INTERFACEHEIGHT), flag)
        while True:
            self.time.tick(30)
            self.monitor()
            self.screen.blit(self.background_image, (0, 0))        
            #是否有焦点,并显示图片
            for other_button in self.other_buttons:
                other_button.focus()
            for button in self.buttons:
                button.focus()
            pygame.display.update()    
            
if __name__ == '__main__':
    interface = Interface()
    interface.run_interface()
        
