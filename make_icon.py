"""Generate a glassmorphism cube icon for the Modern UI Modrinth project."""
import math
from PIL import Image, ImageDraw, ImageFilter

S = 256
img = Image.new("RGBA", (S, S), (0, 0, 0, 0))
px = img.load()

# Dark navy radial background
for y in range(S):
    for x in range(S):
        d = min(1.0, math.hypot(x - 128, y - 128) / 185.0)
        px[x, y] = (int(20 - 13 * d), int(34 - 22 * d), int(66 - 46 * d), 255)

# Soft cyan glow behind the cube
glow = Image.new("RGBA", (S, S), (0, 0, 0, 0))
ImageDraw.Draw(glow).ellipse((44, 36, 212, 204), fill=(130, 225, 255, 105))
img = Image.alpha_composite(img, glow.filter(ImageFilter.GaussianBlur(40)))


def face(points, top, bottom):
    """Polygon filled with a vertical glass gradient."""
    mask = Image.new("L", (S, S), 0)
    ImageDraw.Draw(mask).polygon(points, fill=255)
    grad = Image.new("RGBA", (S, S), (0, 0, 0, 0))
    gp = grad.load()
    for y in range(S):
        f = max(0.0, min(1.0, (y - 36) / 170.0))
        c = tuple(int(top[i] + (bottom[i] - top[i]) * f) for i in range(3)) + (235,)
        for x in range(S):
            gp[x, y] = c
    img.paste(grad, (0, 0), mask)


# Isometric cube: top / left / right faces
top = [(128, 38), (198, 79), (128, 120), (58, 79)]
left = [(58, 79), (128, 120), (128, 204), (58, 163)]
right = [(198, 79), (128, 120), (128, 204), (198, 163)]

face(top, (225, 248, 255), (150, 205, 235))
face(left, (170, 225, 245), (85, 150, 195))
face(right, (205, 240, 255), (110, 185, 225))

d = ImageDraw.Draw(img, "RGBA")

# Specular rim highlights on the upper edges
for pts in (top, left, right):
    d.line(pts + [pts[0]], fill=(235, 252, 255, 210), width=3)
d.line([(128, 38), (128, 120), (198, 79)], fill=(255, 255, 255, 235), width=2)
d.line([(58, 79), (128, 120)], fill=(190, 245, 255, 180), width=2)

# Inner glints
d.line([(70, 88), (70, 155)], fill=(255, 255, 255, 90), width=2)
d.line([(188, 88), (188, 150)], fill=(255, 255, 255, 70), width=2)
d.ellipse((112, 52, 126, 66), fill=(255, 255, 255, 200))

img.save("modrinth-icon.png")
img.resize((64, 64), Image.Resampling.LANCZOS).save("icon64.png")
print("wrote modrinth-icon.png and icon64.png")
