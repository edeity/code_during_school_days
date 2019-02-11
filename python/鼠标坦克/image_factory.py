#_*_coding:utf-8 _*_
import pygame

class ImageFactory:
    '''
    满足游戏载入图片和添加动画的要求
    '''
    def __init__(self):
        pass
    def image_process(self, image_filename, picture_num):
        '''
        
        image_filename :要处理图片的路径名
        picture_num : 要把图片分割的张数
        '''
        processed_images = []
        #为兼容,决定无论是1张还是多张,都返回一个装在图片的列表
        if picture_num == 1:
            processed_images.append(pygame.image.load(image_filename))
        else:
            pending_image = pygame.image.load(image_filename)
            width = pending_image.get_width()
            clip_width = width   #要剪切的长度
            clip_height = pending_image.get_height() / picture_num    #要剪切的高度,一般为其本身
            for i in range(picture_num):
                processed_images.append(pending_image.subsurface(pygame.Rect(0, clip_height * i, clip_width, clip_height)))
        return processed_images  #处理后的图片列表
     
