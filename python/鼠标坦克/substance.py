#_*_ coding:gbk _*_
'''
Created on 2013��9��19��

@author: javer
'''
import pygame
from gameobjects.vector2 import Vector2
from math import *
import random
import time

'''���ӵ����������'''
class  WorldBridge():
    proManager = None
    Substance = None
    time = time.time()
    def __init__(self, proManager):
            WorldBridge.proManager = proManager            
          
class Substance():              #��:����֮ĸ
    def __init__(self,  group, images):       
        #��������
        self.name = 'substance'                   #����
        self.ap = 0                                      #������
        self.life =  1                                  #����ֵ 
        self.speed = 0                             #�ƶ��ٶ�
        self.armor = 0                              #����  
        self.position = Vector2(0, 0)            #�ڵ�ͼ�ϵ�(x, y)����
        self.next_position = Vector2(0, 0)
        self.rotation_position = 0                #��ת��λ��, ������Ϊ0
        self.rotation_speed = 0                   #��ת���ٶ�
        #sprite�����������Ҫ��(images, rect, group)
        self.images = images
        self.image = images[0]                            #�����ͼƬ
        self.images_index = 0                                #��¼��ǰͼƬ������
        self.images_length = len(images)                 #��¼ͼƬһ���ж�����
        self.width = self.image.get_width()                   #��ײ���ĵĳ��Ⱥ͸߶�
        self.height = self.image.get_height()
        self.rect = pygame.Rect(self.position, (self.width, self.height))                #������ʵ�Ĵ�С()
        self.group = group                                 #�������ڵ���,������������������
        self.group.append(self)         
        self.base_group = WorldBridge.proManager.substances     #�����ܵ���ײ����(��������Կ���ײ)
        self.base_group.append(self)
        #������ӵ�״̬,�Ա������ڽ�ɫ������
        self.last_time = 0#����ʱ��,��λ��
        self.statu = 0  #0��������״̬, 1�������״̬, 2�����ж�״̬, 3����, 4����, 5��Ѫ       
    def run(self):#�����˶���������
        if self.speed != 0:
            self.prepare_move()
            self.move_with_change()
        else:
            self.move_without_change()
        '''�ƶ���ִ�����������'''
        self.render_statu()         #��ʾѪ��
        if self.group == WorldBridge.proManager.heros or self.group == WorldBridge.proManager.monsters:
            self.attack()
        #self.render_rect(self.rect)                 #���������ʵ����ײ��ְ  
    def change_statu(self):
        if self.last_time > 0:
            self.last_time = self.last_time - 1
        #����BUFF��Ӧ
            if self.statu == 1:#����
                if self.last_time >= 4:
                    self.speed -= 1
                else :
                    self.speed +=1
            elif self.statu == 2:#�ж�
                self.life = self.life -1
                if self.life <= 0:
                    self.die()#�޲�һ����������Ե��
            elif self.statu == 3:#����
                if self.last_time >= 4:
                    self.speed += 1
                else:
                    self.speed -= 1
            elif self.statu == 4:#�ӻ���
                if self.last_time >= 4:
                    self.armor += 1
                else:
                    self.armor  -= 1
            elif self.statu == 5:#��Ѫ
                self.life += 0.5
        else:
            self.statu = 0
            self.last_time = 0
    def change_image(self):     #�ı�ͼƬ
        if self.images_length != 1:
            self.image = self.images[self.images_index]
            self.images_index += 1
            if self.images_index == self.images_length:
                self.images_index = 0       
    def prepare_move(self):             #����ó��´��ƶ���λ��
        self.next_position = self.position.copy() 
        move_x = cos(self.rotation_position * pi / 180)
        move_y = sin(self.rotation_position * pi / 180)
        move_distance = Vector2(move_x, -move_y)#y�ӷ��źͼ�����������й�
        if self.group == WorldBridge.proManager.heros: #̹�����⴦��
            move_distance *= self.isMove  
        self.next_position = self.next_position + move_distance * self.speed       #�õ����弴���ƶ���λ��
        self.out_of_border()
    def out_of_border(self):#�����Ĵ���  
        '''�����Ĵ���'''
        if self.next_position.x < 10:
            self.next_position.x = 10
        if self.next_position.x > WorldBridge.proManager.FIRELENGTH - 25:
            self.next_position.x = WorldBridge.proManager.FIRELENGTH - 25
        if self.next_position.y < 10:
            self.next_position.y = 10
        if self.next_position.y > WorldBridge.proManager.FIREHEIGHT - 10:
            self.next_position.y = WorldBridge.proManager.FIREHEIGHT - 10  
    def move_with_change(self):             #����ʾ����ͼ,���ı���ײ���ĵ�λ��      
        rotated_image = pygame.transform.rotate(self.image, self.rotation_position)         #��תͼ�� 
        w, h = rotated_image.get_size()                  #��ת��ĳߴ�
        draw_position = Vector2(self.next_position.x  - w / 2 , self.next_position.y  - h / 2)                    #��ת���λ��
        self.rect = pygame.Rect((self.position.x - w/4, self.position.y - h/4) , (self.width / 2, self.height / 2))    
        self.position = self.next_position                #�ı�����λ��,�Ըı���ײ���ĵ�λ��
        WorldBridge.proManager.screen.blit(rotated_image, draw_position)                         #���ֲ�����������                
    def move_without_change(self):
        WorldBridge.proManager.screen.blit(self.image, self.position)    
        self.rect = pygame.Rect(self.position , (self.width, self.height))          
    def attack(self):   #�����ײ,������ײ,������effect()�������������
        for substance in WorldBridge.proManager.substances:
            if self.rect.colliderect(substance.rect) and self != substance:
                if self.group != substance.group :#����ͬһ���
                    substance.effect(self)
                else:#ͬ���ײ����һ��, ��������µ�λ��
                    self.same_ground_collide()
    def same_ground_collide(self):#ͬ����ײ������Ч��,���Ϊ�������³������±ܿ�̹���й���
        temp_position_x = WorldBridge.proManager.myTank.position.x
        temp_position_y = WorldBridge.proManager.myTank.position.y
        if temp_position_x > WorldBridge.proManager.FIRELENGTH / 2:
            appear_x = random.randint(50, WorldBridge.proManager.FIRELENGTH /2)
        else:
            appear_x = random.randint(WorldBridge.proManager.FIRELENGTH / 2, WorldBridge.proManager.FIRELENGTH  - 50)
        if temp_position_y > WorldBridge.proManager.FIREHEIGHT / 2:
            appear_y = random.randint(50, WorldBridge.proManager.FIREHEIGHT / 2)
        else:
            appear_y = random.randint(WorldBridge.proManager.FIREHEIGHT / 2, WorldBridge.proManager.FIREHEIGHT - 50)
        self.position = Vector2(appear_x, appear_y)
    def effect(self, effect_object):#�൱����ͨ����,������д���Ǽ����˺�
        self.life -=(effect_object.ap - self.armor)
        effect_object.life -= (self.ap - effect_object.armor)
        if self.life <= 0:       #����,���remove����һ����������
            self.die()
        if effect_object.life <= 0:
            effect_object.die()           
    def die(self):                      #���Լ��������ų���
        self.group.remove(self)
        self.base_group.remove(self)
    def render_statu(self):           #��ʾӢ�ۺ͹����Ѫ��, �Լ����ڵ�BUFF
        if self.group != WorldBridge.proManager.weapons and self.group != WorldBridge.proManager.buildings:        #�������ǽ�������ʾѪ��
            life_position = self.position - Vector2(self.width / 2 , self.height / 2)
            life_rect = (life_position, (self.life * 10, 5))
            life_color = (255, 0, 0)#red
            pygame.draw.rect(WorldBridge.proManager.screen, life_color, life_rect)           
    def render_rect(self, life_rect):               #�����ʵ����ײ���ĵ�λ�ú���ְ
        #life_position = (self.position.x, self.position.y)#Ѫ��λ��
        life_color = (0, 0, 255)#��ɫ
        pygame.draw.rect(WorldBridge.proManager.screen, life_color, life_rect)
