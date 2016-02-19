/*
 * This file is part of EssentialCmds, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2015 - 2015 HassanS6000
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.hsyyid.essentialcmds.utils;

public class AFK
{
	public long lastMovementTime;
	public boolean AFK = false;
	public boolean messaged = false;
	public int foodLevel;

	public AFK(long lastMovementTime)
	{
		this.lastMovementTime = lastMovementTime;
	}

	public int getFood()
	{
		return this.foodLevel;
	}

	public void setFood(int foodLevel)
	{
		this.foodLevel = foodLevel;
	}

	public boolean getAFK()
	{
		return AFK;
	}

	public void setAFK(boolean AFK)
	{
		this.AFK = AFK;
	}

	public boolean getMessaged()
	{
		return messaged;
	}

	public void setMessaged(boolean messaged)
	{
		this.messaged = messaged;
	}

	public long getLastMovementTime()
	{
		return lastMovementTime;
	}
}
