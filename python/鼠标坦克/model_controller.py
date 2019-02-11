#_*_coding:utf-8_*_
'''
Created on 2013年9月22日

@author: javer
'''
from monsters import *
from buildings import *
import os
from gameobjects.vector2 import Vector2

class  WorldBridge():
    proManager = None
    Substance = None
    def __init__(self, proManager):
            WorldBridge.proManager = proManager
            
class ModelController:
    def __init__(self):
        #model = model     chapter = chapter   level =level
        pass
    def load_model(self, model, chapter, level):
        '''普通模式'''
        if model == 0:
            self.load_simple_model(chapter, level)
        elif model == 1:
            self.load_survive_model(chapter, level)
        elif model == 2:
            self.load_race_model(chapter, level)
        elif model == 3:
            self.load_person_model()
    '''普通关卡'''        
    def load_simple_model(self, chapter, level):
        if chapter == 1:
            self.load_first_chapter(level)
        elif chapter == 2:
            self.load_second_chapter(level)
        elif chapter  == 3:
            self.load_thrid_chapter(level)
        elif chapter == 4:
            self.load_forth_chapter(level)
    '''普通关卡的不同章节'''                    
#1.coconut_image   coral_image   devil_image   dinosaur_image   littledinosaur_image(f)   plant_image   pumpkinman_image   pupa_image   squirrel_image   flower_image  tree_image
    #森林
    def load_first_chapter(self, level):
        if level == 1:
            for i in range(0, 3):   #3个
                Coconut()
                Coral()
        elif level == 2:
            for i in range(0, 3):
                Coral()
                Devil()
        elif level == 3:
            for i in range(0, 3):
                Devil()
                Dinosaur()
        elif level == 4:
            for i in range(0, 3):
                Dinosaur()
                Plant()
        elif level == 5:
            for i in range(0, 3):
                Plant()
                Pumpkinman()
        elif level == 6:
            for i in range(0, 3):
                Pumpkinman()
                Pupa()
        elif level == 7:
            for i in range(0, 3):
                Pupa()
                Squirrel()
        elif level == 8:
            Coconut();Coral();Devil();Dinosaur();Plant();Pumpkinman();Pupa();Squirrel()
        elif level == 9:
            Tree()
#  2.bubble_image   cirrus_image   crabman_image   cuttlefishwizard_image   fishguard_image   fishman_image   lobster_image   scorpion_image   sliverfish_image   
    #深海
    def load_second_chapter(self, level):
        if level == 1:
            for i in range(0, 4):   #3个
                Bubble()
                Cirrus()
        elif level == 2:
            for i in range(0, 4):
                Cirrus()
                Crabman()
        elif level == 3:
            for i in range(0, 4):
                Crabman()
                Sliverfish()
        elif level == 4:
            for i in range(0, 4):
                Sliverfish()
                Fishguard()
        elif level == 5:
            for i in range(0, 4):
                Fishguard()
                Fishman()
        elif level == 6:
            for i in range(0, 4):
                Fishman()
                Lobster()
        elif level == 7:
            for i in range(0, 4):
                Lobster()
                Scorpion()
        elif level == 8:
            for i in range(0, 9):
                Scorpion()
        elif level == 9:
            Cirrus()
            Crabman()
            Sliverfish()
            Fishguard()
            Fishman()
            Lobster()
            Scorpion()
            Cuttlefishwizard()
#  3.boneboy_image   bonecaptain_image   bonewizzard_image   ghost_image   littleghost_image   shortghost_image  mummy_image  armourman_image  bullman_image
    #鬼都
    def load_thrid_chapter(self, level):
        if level == 1:
            for i in range(0, 5):
                Boneboy()
                Bonecaptain()
        elif level == 2:
            for i in range(0, 5):
                Bonecaptain()
                Bonewizzard()
        elif level == 3:
            for i in range(0, 5):
                Bonewizzard()
                Ghost()
        elif level == 4:
            for i in range(0, 5):
                Ghost()
                Littleghost()
        elif level == 5:
            for i in range(0, 5):
                Littleghost()
                Shortghost()
        elif level == 6:
            for i in range(0, 5):
                Shortghost()
                Armourman()
        elif level == 7:
            for i in range(0, 4):
                Ghost()
                Littleghost()
                Shortghost()
        elif level == 8:
            Bullman()
        elif level == 9:
            Mummy()
    #石城
#  4.goldstoneman_image   stone_image   stoneboy_image   stonedog_image   stonegirl_image   stonefish_image   stoneguard_image   stonelion_image   stoneman_image
    def load_forth_chapter(self, level):
        if level == 1:
            Stone()
        elif level == 2:
            Stoneboy()
        elif level == 3:
            Stonedog()
        elif level == 4:
            Stonegirl()
        elif level == 5:
            Stonefish()
        elif level == 6:
            Stoneguard()
        elif level == 7:
            Stonelion()
        elif level == 8:
            Stoneman()
        elif level == 9:
            Goldstoneman()
# 6.bat_image  batman_image   birdman_image   fireball_image   lavaman_image   lavamonster_image   littlebat_image   monkey_image   spider_image   spidergirl_image
    '''生存大挑战的不同章节'''        
    def load_survive_model(self, chapter, level):
        WorldBridge.proManager.myTank.shellNum = 0
        if chapter == 1:
            Tree()
        elif chapter == 2:
            Cuttlefishwizard()
            Sliverfish()
            Sliverfish()
        elif chapter == 3:
            Mummy()
        elif chapter == 4:
            Goldstoneman()
    '''赛车模式的不同章节'''
    def load_race_model(self, chapter, level):
        WorldBridge.proManager.myTank.position = Vector2(0, 700)
        WorldBridge.proManager.myTank.shellNum = 0
        Flower(Vector2(1000, 700))
        if level == 1:
            LongWall(Vector2(200, 200))
            LongWall(Vector2(400, 0))
            LongWall(Vector2(600, 200))
            LongWall(Vector2(800, 0))
            AccelerateBar(Vector2(450, 400))
        elif level == 2:
            LongWall(Vector2(200, 0))
            LongWall(Vector2(400, 200))
            LongWall(Vector2(900, 0))
            ShortWall(Vector2(400, 200))
            ShortWall(Vector2(600, 400))
            ShortWall(Vector2(450, 600))      
        elif level == 3:
            WorldBridge.proManager.time_limit = 60
            LongWall(Vector2(100, 200))
            LongWall(Vector2(300, 0))
            LongWall(Vector2(800, 200))
            ShortWall(Vector2(350, 200))
            Wall(Vector2(400, 400))
            Wall(Vector2((300, 600)))
        elif level == 4:
            WorldBridge.proManager.time_limit = 90
            LongWall(Vector2(100, 0))
            LongWall(Vector2(200, 200))
            LongWall(Vector2(300, 0))
            LongWall(Vector2(400, 200))
            LongWall(Vector2(500, 0))
            LongWall(Vector2(600, 200))
            LongWall(Vector2(700, 0))
            LongWall(Vector2(800, 200))
            LongWall(Vector2(900, 0))
        elif level == 5:
            WorldBridge.proManager.time_limit = 120
            ThinWall(Vector2(200, 0))
            ThinWall(Vector2(800, 0))
            ThinWall(Vector2(500, 200))
            LittleWall(Vector2(220, 200))
            LittleWall(Vector2(300, 300))
            LittleWall(Vector2(220, 400))
            LittleWall(Vector2(300, 500))
            LittleWall(Vector2(220, 580))
            LittleWall(Vector2(600, 200))
            LittleWall(Vector2(520, 300))
            LittleWall(Vector2(600, 400))
            LittleWall(Vector2(520, 500))
            LittleWall(Vector2(600, 580))
            LittleWall(Vector2(520, 700))
        elif level == 6:
            pass
        elif level == 7:
            pass
        elif level == 8:
            pass
        elif level == 9:
            pass
    def load_person_model(self):#创建个人怪兽
        dir_name = 'image/persons/'
        files_name = os.listdir(dir_name)
        for image_filename in files_name:
            temp_images = []
            temp_image =  pygame.image.load(dir_name + image_filename).convert()
            temp_images.append(temp_image)
            Person(temp_images)          
'''
   #根据模式的不同,简单地创建不同的怪兽
        if self.model == 0:  #简单模式
            if level[0] == 0:#第一站
                if level[1] == 0:#第一关
                    monsters.Flower()
                    monsters.Test()
        elif self.model == 1:#生存模式 
            monsters.Tree()
            self.myTank.shellNum = 0
        elif self.model == 2:#赛车模式
            temp_position_x = 100
            temp_position_y = 0
            #下面仅仅是地图
            for i in range(3):
                for j in range(10):
                    buildings.Wood(Vector2(temp_position_x, temp_position_y))
                    temp_position_y += 30
                temp_position_x += 200
                temp_position_y = 0
                
            temp_position_x = 200
            temp_position_y = 370
            for i in range(3):
                for j in range(10):
                    buildings.Wood(Vector2(temp_position_x, temp_position_y))
                    temp_position_y -=30
                temp_position_x += 200
                temp_position_y = 370
            
            flower = monsters.Flower()
            flower.position = Vector2(750, 200)
            self.myTank.position = Vector2(10, 400)
            self.myTank.life = 2
            self.myTank.shellNum = 0     
        elif self.model == 3:#打自己
            dir_name = 'image/persons/'
            files_name = os.listdir(dir_name)
            for image_filename in files_name:
                image = self.simplify_load_image(dir_name + image_filename, True)
                monsters.Person(image)               
'''
