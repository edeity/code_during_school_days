# _*_ coding: utf-8 _*_
import pygame
from sys import exit
import random


'''我自己的类'''
import substance
import hero
import monsters
import buildings
import weapons
import model_controller
from image_factory import *
#    --------------------------------我是华丽的分割线------------------------------------------------------------------------------------
#关卡
model = 0# 0普通模式, 1生存模式, 2赛车模式, 3个人模式, 4测试模式 
chapter = 1#章节
level = 1#关卡
volumn = 1#游戏音量
time_limit = 30 #限制的时间(秒)
#游戏中的各种控制的东西
FPS = 30            #刷新频率
font_color = (0, 0, 0)        #游戏字体颜色
font_style = '' 
font_size = 50
flag = pygame.FULLSCREEN  #pygame.FULLSCREEN      pygame.NOFRAME 无框    0
#1366  768  全屏
GAMELENGTH = 1366
GAMEHEIGHT = 768
#对战时的界面
FIRELENGTH = 1100
FIREHEIGHT = 768
#时间坐标
time_x = 1205
time_y = 60
#坦克状态显示界面
tank_x = FIRELENGTH
tank_y = 160
#可以发射子弹坐标
shoot_x = FIRELENGTH
shoot_y = 580
#背景
magic_forest_image_filename = 'image/backgrounds/magic_forest.jpg'
deep_sea_image_filename = 'image/backgrounds/deep_sea.jpg'
seventeenth_street_image_filename = 'image/backgrounds/seventeenth_street.jpg'
stone_city_image_filename = 'image/backgrounds/stone_city.jpg'
race_image_filename = 'image/backgrounds/race.jpg'
person_image_filename = 'image/backgrounds/person.jpg'
#游戏侧栏的图片
time_image_filename = 'image/backgrounds/time.jpg'
tank_state_image_filename = 'image/backgrounds/state.jpg'
ready_fault_image_filename = 'image/backgrounds/ready0.jpg'
ready_true_image_filename = 'image/backgrounds/ready1.jpg'
gameover_image_filename = 'image/backgrounds/gameover.jpg'
win_image_filename = 'image/backgrounds/win.jpg'
cd_image_filename = 'image/backgrounds/cd.png'
#声音
background_sound_filename = "music/fight.mp3"
explode_sound_filename = "music/explode.wav"        
#   ---------------------------------------------------------------我是华丽的分割线--------------------------------------------------------------------
class InterfaceBridge:
    interface = None
    def __init__(self, interface):
            InterfaceBridge.interface = interface       
class ProgramManager:           #程序控制员
    def __init__(self):
        #声音初始化,分别对应声音文件的采样率, 量化精度, 立体声效果, 缓冲大小
        pygame.mixer.pre_init(44100, 16, 2, 4096)
        pygame.init()
        pygame.display.set_caption('Mouse Tank War')

        # 设置屏幕(第一个是分辨率,第二个是flag,第三个是色深)
        self.GAMELENGTH = GAMELENGTH  
        self.GAMEHEIGHT = GAMEHEIGHT
        self.FIRELENGTH = FIRELENGTH
        self.FIREHEIGHT = FIREHEIGHT
        self.flag = flag
        self.screen = pygame.display.set_mode((self.GAMELENGTH , self.GAMEHEIGHT), self.flag)
        self.clock = pygame.time.Clock()                      # 设置时钟计算频率       
        
        #属性
        self.model =model 
        self.chapter = chapter
        self.level = level
        self.pass_time= 0      
        self.time_limit = time_limit
        self.background_image = None
        #创建世界的桥梁
        substance.WorldBridge(self)
        hero.WorldBridge(self)
        monsters.WorldBridge(self)
        weapons.WorldBridge(self)
        buildings.WorldBridge(self)     
        model_controller.WorldBridge(self)
        #预处理
        self.loadFont()
        self.loadImage()
    def loadFont(self):             #加载游戏中的文字类型
        self.during_game_font = pygame.font.SysFont(font_style , font_size) 
    def loadImage(self):
        #装载游戏中用到的背景图片
        image_factory = ImageFactory()
        self.magic_forest_image = image_factory.image_process(magic_forest_image_filename, 1)[0]
        self.deep_sea_image = image_factory.image_process(deep_sea_image_filename, 1)[0]
        self.seventhteen_street_image = image_factory.image_process(seventeenth_street_image_filename, 1)[0]
        self.stone_city_image = image_factory.image_process(stone_city_image_filename, 1)[0]   
        self.race_image = image_factory.image_process(race_image_filename, 1)[0]
        self.person_image = image_factory.image_process(person_image_filename, 1)[0]
        #游戏用到的侧栏图片
        self.time_image = image_factory.image_process(time_image_filename, 1)[0]
        self.tank_state_image = image_factory.image_process(tank_state_image_filename, 1)[0]
        self.ready_fault_image = image_factory.image_process(ready_fault_image_filename, 1)[0]
        self.ready_true_image = image_factory.image_process(ready_true_image_filename, 1)[0]
    def loadSound(self):                    #加载爆炸声音
        self.explodeSound =pygame.mixer.Sound(explode_sound_filename)
        pygame.mixer.music.load(background_sound_filename)          #加载背景音乐
        pygame.mixer.music.set_volume(volumn)                      #设置背景音量
        pygame.mixer.music.play()           #开始播放背景音乐       
    def loadModel(self):                    #加载游戏模式
        self.substances = []
        self.buildings = []
        self.monsters = []
        self.heros = []
        self.weapons = []
        #选择地图
        if model == 0 or model == 1:
            if self.chapter == 1:
                self.background_image = self.magic_forest_image
            elif self.chapter == 2:
                self.background_image = self.deep_sea_image
            elif self.chapter == 3:
                self.background_image = self.seventhteen_street_image
            elif self.chapter == 4:
                self.background_image = self.stone_city_image
        elif model == 2:
            self.background_image = self.race_image
        elif model == 3:
            self.background_image = self.person_image
        #创建实例
        self.myTank = hero.Tank()
        self.myTankShells = []
        mc = model_controller.ModelController()
        mc.load_model(model, chapter, level)     
   
    def start_game(self):            #死循环代表游戏的开始
        self.during_game = True
        self.FPS = FPS
        self.time_counter = 0                        #没增加1代表时间过了1/FPS秒, 时间只是大体上的
        self.end_game_kind = 0                    #游戏结束, 输了(0), 赢了(1)
        pygame.mouse.set_visible(0)
        pygame.event.set_grab(1)                 #鼠标受限于游戏内
        #在开始时就将背景画上, 以后再更新
        self.screen.blit(self.background_image, (0, 0), ((0, 0), (self.FIRELENGTH, self.FIREHEIGHT)))
        self.screen.blit(self.time_image, (1100, 0))  
        self.screen.blit(self.tank_state_image, (tank_x, tank_y))
        self.screen.blit(self.ready_true_image, (shoot_x, shoot_y))
        self.render_time()
        
        '''下面是游戏的主题体'''
        while self.during_game:       
            self.monitor()   #设置监听器
            self.clock.tick(self.FPS) #设置时钟
            self.count_time() 
            '''背景图片'''
            self.screen.blit(self.background_image, (0, 0), ((0, 0), (self.FIRELENGTH, self.FIREHEIGHT)))
            ''' 根据时间的不同改变操作'''       
            #每秒刷新一次             
            if self.time_counter % 30 == 0:
                #监听是否游戏结束
                self.monitor_end_game()
                #随机产生补给
                self.random_egg()
                #刷新状态栏
                self.screen.blit(self.time_image, (1100, 0))  
                self.screen.blit(self.tank_state_image, (tank_x, tank_y))
                #填充子弹
                if self.myTank.ready_to_shoot:
                    self.screen.blit(self.ready_true_image, (shoot_x, shoot_y))
                self.myTank.ready_to_shoot = True   
                #改变状态                                          #计算时间
                self.myTank.change_statu()
                for monster in self.monsters:
                    monster.change_statu()   
                #显示时间
                self.render_time()                   
            #没0.25秒刷新一次        
            if self.time_counter % 4 == 0:
                for monster in self.monsters:
                    monster.change_image()  
                if self.time_counter % 16 == 0:
                    for weapon in self.weapons: 
                        weapon.change_image()            
            #检测输赢
            #开始运动(CPU之伤)
            for substance in self.substances:
                substance.run()     
                     
            pygame.display.update()                         #不断刷新
        else:          
            self.end_game()
    def count_time(self):           #计算从开始经过的时间, 一个游戏只允许允许调用这个方法1次
        self.time_counter += 1
        if self.time_counter % self.FPS == 0:           #求的是余数,是整数的时候便返回
            self.pass_time = self.time_counter / self.FPS
    def render_time(self):                 #在屏幕上显示时间  
        str_time = 0
        undone_time = 0#未处理的时间
        if self.model == 2:
            undone_time = self.time_limit - self.pass_time
        else :#非生存模式
            undone_time = self.pass_time
        #处理时间
        if undone_time <10:
            str_time = '00' + str(undone_time)
        elif undone_time < 100:
            str_time = '0' + str(undone_time)
        elif undone_time < 1000:
            str_time = str(undone_time)
        else:#超过1000秒视为输掉游戏
            self.during_game = False
            self.end_game_kind = 0
        #投影到屏幕上
        time_surface = self.during_game_font.render(str_time, True, font_color)
        self.screen.blit(time_surface, (time_x, time_y))
    def random_egg(self):#随机产生补给
        if self.pass_time % 16 == 0:
            temp_egg_num = random.randint(1, 5)
            if temp_egg_num == 1:
                weapons.IceEgg(None)
            elif temp_egg_num == 2:
                weapons.PoisonEgg(None)
            elif temp_egg_num == 3:
                weapons.SpeedEgg(None)
            elif temp_egg_num == 4:
                weapons.ArmorEgg(None)
            elif temp_egg_num == 5:
                weapons.LifeEgg(None)
        pass
    def monitor(self):                  #游戏的监听器
        pressed_mouse = pygame.mouse.get_pressed()     #鼠标监听:返回一个数组(0, 0, 0)第一个表示左键, 第三个表示右键, 按下表示值为1    
        #键盘监听            pressed_keys = pygame.key.get_pressed()        
        self.myTank.isMove = 0.                     #0代表停, 1代表前进      
        for event in pygame.event.get():
            # esc退出
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    exit()
                if event.key == pygame.K_q:#强制退出
                    self.during_game = False
                    self.end_game_kind = 0
            if event.type == pygame.MOUSEBUTTONDOWN:
                if event.button == 1 and self.myTank.shellNum != 0 and self.myTank.ready_to_shoot:
                    self.myTank.shoot()
                    self.myTank.ready_to_shoot = False
                    self.screen.blit(self.ready_fault_image, (shoot_x, shoot_y))
                elif event.button == 4:#向上更换武器
                    self.myTank.equipment_kind -= 1
                    if self.myTank.equipment_kind  < 0:#小于装备总数
                        self.myTank.equipment_kind = len(self.myTank.equipments) - 1
                    self.myTank.equipment = self.myTank.equipments[self.myTank.equipment_kind]
                    self.myTank.equipment_change()
                elif event.button == 5:#向下更换武器
                    self.myTank.equipment_kind += 1
                    if self.myTank.equipment_kind  >= len(self.myTank.equipments):#大于装备总数
                        self.myTank.equipment_kind = 0
                    self.myTank.equipment = self.myTank.equipments[self.myTank.equipment_kind]
                    self.myTank.equipment_change()
        #下面非上面for循环,若在for循环里面,只能是点击一下
        if pressed_mouse[2]:        #键盘鼠标的右键表示移动
            self.myTank.isMove = 1
    def monitor_end_game(self):#监听可能使游戏结束的结果
        if self.model == 2:
            if self.pass_time == self.time_limit:
                self.during_game = False
                self.end_game_kind = 0
        if len(self.monsters) == 0:    #怪兽数量为零,赢得游戏
            self.during_game = False
            self.end_game_kind = 1
        elif len(self.heros) == 0:
            self.during_game = False
            self.end_game_kind = 0
    def end_game(self):              #游戏结束的效果
        pygame.mouse.set_visible(1)
        pygame.event.set_grab(0)
        InterfaceBridge.interface.return_interface(self.model, self.end_game_kind)
        '''游戏结束''' 
        '''
        while True:
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    exit()
                if event.type == pygame.KEYDOWN:
                    if event.key == pygame.K_ESCAPE:
                        exit()
            self.screen.blit(self.background_image, (0, 0))                   #屏幕开始位置
            self.clock.tick(2)                       #频率降到最低
            #显示字样   
            if self.end_game_kind == 0:
                self.screen.blit(self.gameover_image, (0,0))
            elif self.end_game_kind == 1:
                self.screen.blit(self.win_image, (0,0))
            pygame.display.update()
        self.loadSound()
        '''
    def run(self):
        self.loadSound()
        self.loadModel()
        self.start_game()
